package shellUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Used to execute commands easily in a shell automatically reads output using a separate thread
 * 
 * @author Allen
 *
 */
public class Terminal {
	protected Process p;
	protected BufferedWriter p_in;
	protected BufferedReader p_out;
	protected BufferedReader p_error;
	
	protected long readWait=TimeUnit.SECONDS.toMillis(5);
	
	protected Vector<String> stdout=new Vector<String>();
	protected Vector<String> stderr=new Vector<String>();
	protected boolean exit=false;
	protected ExecutorService s=Executors.newSingleThreadExecutor();
	
	private Object outLock=new Object();
	private Object errLock=new Object();
	private boolean selfExc=false;
	
	/**
	 * 
	 * @param executionDirectory file in which command file exists/script
	 * @param cmd command file to launch terminal process in windows this is cmd.exe on mac it is sh
	 * @throws IOException if file does not exist
	 */
	public Terminal(File executionDirectory,String cmd)throws IOException{
		this(executionDirectory,cmd,250);
	}
	/**
	 * 
	 * @param executionDirectory file in which command file exists/script
	 * @param cmd command file to launch terminal process in windows this is cmd.exe on mac it is sh
	 * @param readWait refresh timer for the reader, default set to 250ms lower this to increase speed/processing consumption
	 * @throws IOException if file does not exist
	 */
	public Terminal(File executionDirectory,String cmd,int readWait) throws IOException{
		this(executionDirectory,cmd,readWait,Executors.newSingleThreadExecutor());
		selfExc=true;
	}
	/**
	 * 
	 * @param executionDirectory file in which command file exists/script
	 * @param cmd command file to launch terminal process in windows this is cmd.exe on mac it is sh
	 * @param readWait refresh timer for the reader, default set to 250ms lower this to increase speed/processing consumption
	 * @param service executor service to use for the reader thread to lower thread overhead, and issues with not exiting
	 * @throws IOException if file does not exist
	 */
	public Terminal(File executionDirectory, String cmd,int readWait,ExecutorService service) throws IOException{
		//initialize process builder to use mac 
		ProcessBuilder b=new ProcessBuilder();
		b.directory(executionDirectory);
		b.command(cmd);
		p = b.start();
		
		//initialize everything to monitor process
		this.readWait=readWait;
		p_in = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		p_out=new BufferedReader(new InputStreamReader(p.getInputStream()));
		p_error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
		//schedule with internal scheduler
		s.execute(()->reader());
		
	}
	//single method to read output at 5 second intervals
	private synchronized void reader(){
		while(!exit){//infinite loop until process is exited
			read();
			try {
				this.wait(readWait);
			} catch (InterruptedException e) {
			}
		}
		read();
		this.notifyAll();
	}
	
	/**
	 * Generic read, both regular and error output
	 */
	private synchronized void read(){
		try{
			while(p_out.ready()){
				stdout.add(p_out.readLine());
			}
			synchronized(outLock){
				outLock.notifyAll();
			}
			while(p_error.ready()){
				stderr.add(p_error.readLine());
			}
			synchronized(errLock){
				errLock.notifyAll();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public synchronized List<String> getResponse(){
		List<String> response=new Vector<String>();
		response.addAll(stdout);
		response.addAll(stderr);
		return response;
	}
	/**
	 * Returns output from the terminal process
	 * @return
	 */
	public synchronized List<String> getOut(){
		return stdout;
	}
	/**
	 * prints output from terminal process
	 */
	public synchronized void PrintOut(){
		for(String s:stdout){
			System.out.println(s);
		}
	}
	/**
	 * returns error messages from terminal process
	 * @return
	 */
	public synchronized List<String> getErr(){
		return stderr;
	}
	/**
	 * prints error messages from terminal process
	 */
	public synchronized void PrintErr() {
		for(String s:stderr){
			System.out.println(s);
		}
	}
	/**
	 * blocks until total output lines is equal to or greater than lines
	 * @param lines
	 */
	public void awaitOut(int lines){
		while(stdout.size()<lines){
			try {
				synchronized(outLock){
					outLock.wait();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	/**
	 * blocks until total error output lines is equal to or greater than lines
	 * @param lines
	 */
	public void awaitErr(int lines){
		while(stderr.size()<lines){
			try{
				synchronized(errLock){
					errLock.wait();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	/**
	 * blocks until total lines from all output sources is equal to or greater than lines
	 * @param lines
	 */
	public void awaitResponse(int lines){
		while(stderr.size()+stdout.size()<lines){
			try{
				synchronized(errLock){
					errLock.wait();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	public synchronized void clearResponses(){
		clearOutput();
		clearErrors();
	}
	public synchronized void clearOutput(){
		stdout.clear();
	}
	public synchronized void clearErrors(){
		stderr.clear();
	}
	public void execute(String... commands){
		for(String s:commands){
			try {
				p_in.write(s);
				p_in.newLine();
				p_in.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * blocks until command exits use {@link #forceExit()} to force exit immediately
	 */
	public synchronized void exit(){
		try {
			//type exit into the shell command
			p_in.write("exit");
			p_in.newLine();
			p_in.flush();

			
			p.waitFor();
			
			exit=true;
			this.notifyAll();
			this.wait();
			
			
			//close readers/writers			
			p_out.close();
			p_error.close();
			p_in.close();
			if(selfExc){
				s.shutdown();
			}
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void forceExit(){
		exit=true;
		try {
			p_in.write("exit");
			p_in.newLine();
			p_in.flush();
			this.notifyAll();
			this.wait();
			//close readers/writers
			p_out.close();
			p_error.close();
			p_in.close();
			if(selfExc){
				s.shutdown();
			}
			
			//destroy process if not exited
			if(!p.isAlive())p.destroy();
			int i=0;
				if(p.isAlive()){
					i++;
					//TimeUnit.SECONDS.wait(5);
					if(i>=4){
						p.destroyForcibly();
					}
				}

		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Escapes strings for parameter stuffs on mac OS X
	 */
	public static String escapeString(String escape){
		return escape.replace(" ", "\\ ");
	}
	public void changeDir(File directory){
		Vector<String> commands=new Vector<String>();
		commands.add("cd "+Terminal.escapeString(directory.toString()));
	}
	
}

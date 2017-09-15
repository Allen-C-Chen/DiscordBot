package shellUtil;

import java.io.File;
import java.io.IOException;

/**
 * Used to build a terminal class and setup the various things related to it
 * @author Allen
 *
 */
public class TerminalBuilder{
	private File executionDirectory;
	private String cmd;
	public TerminalBuilder(){
	}
	public TerminalBuilder(File executionDirectory){
		this.executionDirectory=executionDirectory;
	}
	public TerminalBuilder(String cmd){
		this.cmd=cmd;
	}
	public Terminal build()throws IOException{
		if(cmd.equals("null")||cmd==null){
			throw new NullPointerException("the command value cannot be null");
		}
		else if(executionDirectory==null){
			throw new NullPointerException("the execution directory cannot be null");
		}
		else if(!executionDirectory.isDirectory()){
			throw new IOException("execution directory must be a directory");
		}
		return new Terminal(executionDirectory,cmd);
	}
	
}

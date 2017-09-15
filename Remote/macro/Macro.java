package macro;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Macro {
	private EventChain chain;
	private MacroHookManager hooks;
	
	public Macro(){
		chain = new EventChain();
		hooks=new MacroHookManager(chain);
	}
	protected Macro(String[] data){
		chain=EventChain.Load(data);
		hooks=new MacroHookManager(chain);
	}
	public void setDebugging(boolean debug){
		hooks.setDebug(debug);
	}
	public void record(boolean async) throws NativeHookException{
		//add mouse hooks for simple macro recording if none set
		addHooks(Hooks.MouseHook);
		
		// Get the logger for "org.jnativehook" and set the level to warning. to avoid flood of text if not debugging
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(hooks);
		GlobalScreen.addNativeMouseListener(hooks);
		GlobalScreen.addNativeMouseMotionListener(hooks);
		if(!async){
			while(GlobalScreen.isNativeHookRegistered()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		}
	}
	public void endRecord() throws NativeHookException{
		GlobalScreen.unregisterNativeHook();
	}
	/**
	 * Sets key to press to end recording
	 * @param keyCode uses linux keycodes for mac
	 */
	public void setRecordKey(int keyCode){
		hooks.setKey(keyCode);
	}
	/**
	 * Sets key to press to do a color check
	 * @param keyCode uses linux keycodes for mac
	 */
	public void setColorKey(int keyCode){
		hooks.setColor(keyCode);
	}
	public void setapp(String app){
		hooks.setApp(app);
	}
	public void replay() throws AWTException{
		chain.replay();
	}
	public void addHooks(Hooks... hooks){
		this.hooks.addHooks(hooks);
	}
	public void removeHooks(Hooks... hooks){
		this.hooks.removeHooks(hooks);
	}
	public void save(String file) throws IOException{
		File f=new File(file);
		BufferedWriter out=new BufferedWriter(new FileWriter(f));
		String[] data=chain.getData();
		for(String s:data){
			out.write(s+"\n");
		}
		out.close();
	}
	public static Macro load(String file) throws IOException{
		File f=new File(file);
		BufferedReader in=new BufferedReader(new FileReader(f));
		Vector<String> data=new Vector<String>();
		while(in.ready()){
			data.add(in.readLine());
		}
		in.close();
		return new Macro(data.toArray(new String[data.size()]));
	}
}

package macro.events;

import java.awt.Robot;

import macro.MacroException;
/**
 * Any event recorded by jnativehook, attaches time stamp to replay
 * @author Allen
 *
 */
public abstract class MacroEvent {
	public String description;//short description if necessary;
	private Long time;
	public MacroEvent(Long time){
		this.time=time;
	}
	public Long getTime(){
		return time;
	}
	public abstract void action(Robot r) throws MacroException;
	public abstract String getData();
	@Override
	public String toString(){
		return description +" at "+time;
	}
}

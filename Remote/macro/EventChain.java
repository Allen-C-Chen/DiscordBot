package macro;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Vector;

import macro.events.*;

public class EventChain {
	private Vector<MacroEvent> events=new Vector<MacroEvent>();
	private Vector<Long> delay=new Vector<Long>();
	
	public void addEvent(MacroEvent e){
		if(events.size()==0){
			events.add(e);
		}
		else{
			long delay=(e.getTime()-events.lastElement().getTime());
			if(delay==0)return;
			this.delay.add(delay);
			events.add(e);
		}
	}
	public void replay() throws AWTException{
		Robot r=new Robot();
		try{
		for(int i=0;i<events.size();i++){
			events.get(i).action(r);
			try {
				if(i<delay.size()){
					Thread.sleep(delay.get(i));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}catch(MacroException e){//for now it is to catch color check failure, may be used to execute logic in the future
			System.out.println("Check failed");
		}
	}
	public String[] getData(){
		Vector<String> data=new Vector<String>();
		for(int i=0;i<events.size();i++){
			data.add(events.get(i).getData());
			if(i<delay.size()){
				data.add("Delay "+delay.get(i));
			}
		}
		return data.toArray(new String[data.size()]);
	}
	public static EventChain Load(String[] data){
		EventChain chain=new EventChain();
		Long time=0L;
		for(String s:data){
			try{
			String[] items=s.split(" ");
			if(items[0].contains("Delay")){
				time+=Long.parseLong(items[1]);
			}
			else if(items[0].contains("KeyPress")){
				int key=Integer.parseInt(items[1]);
				chain.addEvent(new KeyPressEvent(time,key));
			}
			else if(items[0].contains("KeyRelease")){
				int key=Integer.parseInt(items[1]);
				chain.addEvent(new KeyReleaseEvent(time,key));
			}
			else if(items[0].contains("KeyTyped")){
				int key=Integer.parseInt(items[1]);
				chain.addEvent(new KeyTypedEvent(time,key));
			}
			else if(items[0].contains("MousePress")){
				int button=Integer.parseInt(items[1]);
				chain.addEvent(new MousePressEvent(time,button));
			}
			else if(items[0].contains("MouseRelease")){
				int button=Integer.parseInt(items[1]);
				chain.addEvent(new MouseReleaseEvent(time,button));
			}
			else if(items[0].contains("MouseClick")){
				int button=Integer.parseInt(items[1]);
				chain.addEvent(new MouseClickEvent(time,button));
			}
			else if(items[0].contains("MouseMove")){
				int x=Integer.parseInt(items[1]);
				int y=Integer.parseInt(items[2]);
				chain.addEvent(new MouseMoveEvent(time,x,y));
			}
			else if(items[0].contains("MouseDrag")){
				int x=Integer.parseInt(items[1]);
				int y=Integer.parseInt(items[2]);
				chain.addEvent(new MouseDragEvent(time,x,y));
			}
			else if(items[0].contains("ColorCheck")){
				int x=Integer.parseInt(items[1]);
				int y=Integer.parseInt(items[2]);
				String color=items[3];
				String app=items[4];
				chain.addEvent(new ColorEvent(time,x,y,color,app));
			}
			}catch(Exception e){
				System.out.println("Error parsing "+s);
			}
		}
		return chain;
	}
}

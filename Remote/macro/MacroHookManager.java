package macro;

import java.awt.Point;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import macro.events.*;


public class MacroHookManager implements NativeMouseInputListener,NativeKeyListener{
	private boolean click=false;
	private boolean mpress=false;
	private boolean mrelease=false;
	private boolean move=false;
	private boolean drag=false;
	private boolean kpress=false;
	private boolean krelease=false;
	private boolean type=false;
	private boolean debug=false;
	
	
	private int recordKey=-1;
	private int colorKey=-1;
	
	private ColorEvent c;//holds event to await mouse event only if color check is at beginning
	private Point mouse=null;
	private String app="";//app to use, "" means ignore
	private EventChain chain;
	
	public MacroHookManager(EventChain chain){
		this.chain=chain;
	}
	public void setKey(int keyCode){
		this.recordKey=keyCode;
	}
	public void setColor(int keyCode){
		this.colorKey=keyCode;
	}
	public void setApp(String app){
		this.app=app;
	}
	//verbose printing of 
	public void setDebug(boolean debug){
		this.debug=debug;
	}
	public void addHooks(Hooks... hooks){
		for(Hooks h:hooks){
			switch(h){
			case KeyHook: 
				kpress=krelease= true;
				type=false;
				break;
			case KeyPressHook:
				kpress=true;
				type=false;
				break;
			case KeyReleaseHook:
				krelease=true;
				type=false;
				break;
			case KeyTypedHook:
				type=true;
				kpress=krelease= false;
				break;
			case MouseButtonHook:
				mpress=mrelease=true;
				click=false;
				break;
			case MouseClickHook:
				click=true;
				mpress=mrelease=false;
				break;
			case MouseDragHook:drag=true;
				break;
			case MouseHook:
				mpress=mrelease=move=drag=true;
				click=false;
				break;
			case MouseMoveHook:move=true;
				break;
			case MouseMovementHook:move=drag=true;
				break;
			case MousePressHook:
				mpress=true;
				click=false;
				break;
			case MouseReleaseHook:
				mrelease=true;
				click=false;
				break;
			default:
				break;
			}
		}
	}
	public void removeHooks(Hooks... hooks){
		for(Hooks h:hooks){
			switch(h){
			case KeyHook: kpress=krelease=type= false;
				break;
			case KeyPressHook:kpress=false;
				break;
			case KeyReleaseHook:krelease=false;
				break;
			case KeyTypedHook:type=false;
				break;
			case MouseButtonHook:mpress=mrelease=click=false;
				break;
			case MouseClickHook:click=false;
				break;
			case MouseDragHook:drag=false;
				break;
			case MouseHook:click=mpress=mrelease=move=drag=false;
				break;
			case MouseMoveHook:move=false;
				break;
			case MouseMovementHook:move=drag=false;
				break;
			case MousePressHook:mpress=false;
				break;
			case MouseReleaseHook:mrelease=false;
				break;
			default:
				break;
			}
		}
	}
	
	//listeners
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		if(debug){
			System.out.println("Mouse clicked:"+e.getButton());
		}
		if(click){
			chain.addEvent(new MouseClickEvent(System.currentTimeMillis(), e.getButton()));
		}
		registerColorCheck(e);
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		if(debug){
			System.out.println("Mouse pressed:"+e.getButton());
		}
		if(mpress){
			chain.addEvent(new MousePressEvent(System.currentTimeMillis(),e.getButton()));
		}
		registerColorCheck(e);
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		if(debug){
			System.out.println("Mouse released:"+e.getButton());
		}
		if(mrelease){
			chain.addEvent(new MouseReleaseEvent(System.currentTimeMillis(), e.getButton()));
		}
		registerColorCheck(e);
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		if(debug){
			System.out.println("Mouse moved:"+e.getX()+","+e.getY());
		}
		if(move){
			chain.addEvent(new MouseMoveEvent(System.currentTimeMillis(),e.getX(),e.getY()));
		}
		registerColorCheck(e);
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		if(debug){
			System.out.println("Mouse Dragged:"+e.getX()+","+e.getY());
		}
		if(drag){
			chain.addEvent(new MouseDragEvent(System.currentTimeMillis(),e.getX(),e.getY()));
		}
		registerColorCheck(e);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		if(debug){
			System.out.println("Key Typed:"+e.getKeyCode()+" "+e.getKeyChar());
		}
		if(type){
			chain.addEvent(new KeyTypedEvent(System.currentTimeMillis(), e.getKeyCode()));
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(debug){
			System.out.println("Key Pressed:"+e.getKeyCode()+" "+e.getKeyChar());
		}
		if(e.getKeyCode()==recordKey){
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getKeyCode()==colorKey){//generic color check stuff, grabs color
			ColorEvent c;
			if(mouse==null){
				c=new ColorEvent(System.currentTimeMillis(),app);
				this.c=c;
			}
			else{
				c=new ColorEvent(System.currentTimeMillis(),(int)mouse.getX(),(int)mouse.getY(),ColorUtil.getColor((int)mouse.getX(), (int)mouse.getY()),app);
			}
			chain.addEvent(c);
		}
		if(kpress){
			chain.addEvent(new KeyPressEvent(System.currentTimeMillis(),e.getKeyCode()));
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(debug){
			System.out.println("Key Released:"+e.getKeyCode()+" "+e.getKeyChar());
		}
		if(krelease){
			chain.addEvent(new KeyReleaseEvent(System.currentTimeMillis(),e.getKeyCode()));
		}
	}
	//grabs mouse coordinates on next mouse press event and does the actual check, since you can't get the info from the key event 
	private void registerColorCheck(NativeMouseEvent e){
		if(!(c==null)){
			c.setColorParams(e.getX(), e.getY(), ColorUtil.getColor(e.getX(), e.getY(), app));
		}
		//register x,y coordinates
		mouse=new Point(e.getX(),e.getY());
	}
	

}

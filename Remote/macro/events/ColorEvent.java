package macro.events;

import java.awt.Robot;

import macro.ColorCheckFailed;
import macro.ColorUtil;
import macro.MacroException;

public class ColorEvent extends MacroEvent {
	private int x;
	private int y;
	private String color;
	private String app;
	public ColorEvent(Long time,String app) {
		super(time);
		this.app=app;
	}
	public ColorEvent(Long time,int x, int y, String color, String app){
		this(time,app);
		this.x=x;
		this.y=y;
		this.color=color;
		this.app=app;
	}
	public void setColorParams(int x, int y, String color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	@Override
	public void action(Robot r) throws MacroException {
		if(!ColorUtil.colorCheck(x, y, color, app)){
			throw new ColorCheckFailed("Color at point did not match color in records:"+color);
		}
	}

	@Override
	public String getData() {
		return "ColorCheck "+x+" "+y+" "+color+" "+app;	
	}

}

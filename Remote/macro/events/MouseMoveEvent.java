package macro.events;

import java.awt.Robot;

public class MouseMoveEvent extends MacroEvent {
	private int x;
	private int y;
	public MouseMoveEvent(Long time,int x,int y) {
		super(time);
		this.x=x;
		this.y=y;
	}

	@Override
	public void action(Robot r) {
			r.mouseMove(x, y);
	}

	@Override
	public String getData() {
		return "MouseMove "+x+" "+y;
	}

}

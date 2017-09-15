package macro.events;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseReleaseEvent extends MacroEvent {
	private int button;
	public MouseReleaseEvent(Long time,int button) {
		super(time);
		this.button=button;
	}

	@Override
	public void action(Robot r) {
		int mask=InputEvent.getMaskForButton(button);
		r.mouseRelease(mask);

	}

	@Override
	public String getData() {
		return "MouseRelease "+button;
	}

}

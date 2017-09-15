package macro.events;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseClickEvent extends MacroEvent {
	private int button;
	public MouseClickEvent(Long time,int button) {
		super(time);
		this.button=button;
	}

	@Override
	public void action(Robot r) {
		int mask=InputEvent.getMaskForButton(button);
		r.mousePress(mask);
		r.mouseRelease(mask);
	}

	@Override
	public String getData() {
		return "MouseClick"+button;
	}
}

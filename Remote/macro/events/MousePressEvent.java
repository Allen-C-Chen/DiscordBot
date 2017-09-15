package macro.events;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MousePressEvent extends MacroEvent {
	private int button;
	/**
	 * Records a keypress, WARNING if a keyclick is also recorded, may result in double click
	 * @param time
	 * @param button
	 */
	public MousePressEvent(Long time,int button) {
		super(time);
		this.button=button;
	}

	@Override
	public void action(Robot r) {
		int mask=InputEvent.getMaskForButton(button);
		r.mousePress(mask);
	}

	@Override
	public String getData() {
		return "MousePress "+button;
	}

}

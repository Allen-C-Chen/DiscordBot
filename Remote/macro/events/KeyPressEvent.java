package macro.events;

import java.awt.Robot;

public class KeyPressEvent extends MacroEvent {
	private int key;
	public KeyPressEvent(Long time, int key) {
		super(time);
		this.key=key;
	}

	@Override
	public void action(Robot r) {
		r.keyPress(key);
	}

	@Override
	public String getData() {
		return "KeyPress "+key;
	}

}

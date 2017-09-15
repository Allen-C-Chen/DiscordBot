package macro.events;

import java.awt.Robot;

public class KeyTypedEvent extends MacroEvent {
	int key;
	public KeyTypedEvent(Long time, int key) {
		super(time);
		this.key=key;
	}

	@Override
	public void action(Robot r) {
		r.keyPress(key);
		r.keyRelease(key);
	}

	@Override
	public String getData() {
		return "KeyTyped "+key;
	}

}

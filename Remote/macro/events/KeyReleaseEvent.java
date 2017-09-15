package macro.events;

import java.awt.Robot;

public class KeyReleaseEvent extends MacroEvent {
	private int key;
	public KeyReleaseEvent(Long time,int key) {
		super(time);
		this.key=key;
	}

	@Override
	public void action(Robot r) {
		r.keyRelease(key);
	}

	@Override
	public String getData() {
		return "KeyRelease "+key;
	}

}

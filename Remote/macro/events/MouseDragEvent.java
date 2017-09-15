package macro.events;

import java.awt.Robot;
/**
 * Synonymous with {@link MouseMoveEvent}
 * @author Allen
 *
 */
public class MouseDragEvent extends MacroEvent {
	private int x;
	private int y;
	public MouseDragEvent(Long time, int x, int y) {
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
		return "MouseDrag "+x+" "+y;
	}
	

}

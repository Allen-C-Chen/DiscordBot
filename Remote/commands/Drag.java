package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.click.Clicker;

public class Drag extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Clicker.drag(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
	}

	@Override
	public void help(MessageReceivedEvent event) {
	}

}

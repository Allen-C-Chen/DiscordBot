package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.click.Clicker;

public class Click extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		int x=Integer.parseInt(args[0]);
		int y=Integer.parseInt(args[1]);
		Clicker.click(x, y);
	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

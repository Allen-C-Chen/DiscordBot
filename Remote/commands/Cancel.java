package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Cancel extends CommandGenerics {
	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Delay.kill();
	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

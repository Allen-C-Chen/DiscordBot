package commands;

import java.util.concurrent.Executors;

import global.record.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TKill extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Settings.executor.shutdownNow();
		Settings.executor=Executors.newScheduledThreadPool(30);
	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

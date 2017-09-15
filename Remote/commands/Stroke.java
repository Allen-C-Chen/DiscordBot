package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;
import util.click.Clicker;

public class Stroke extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String c="";
		for(String s:args){
			c+=" "+s;
		}
		Clicker.stroke(c.substring(1));
	}

	@Override
	public void help(MessageReceivedEvent event) {
		String s="enter, back, ";
		Lib.sendMessage(event, s);

	}

}

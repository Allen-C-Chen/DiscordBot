package commands;

import macro.Macro;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;

public class MacroReplay extends CommandGenerics {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try{
			Macro.load(args[0]).replay();;
			Lib.sendMessage(event, "Macro Replay Finished "+args[0]);
		}catch(Exception e){
			Lib.sendMessage(event,"Error occured: "+e.getMessage());
		}

	}
	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

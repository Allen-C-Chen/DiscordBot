package commands;

import global.record.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.terminal.Terminal;

public class Shell2 extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Settings.executor.execute(()->{
			try{
			Terminal t=new Terminal();
			String execute="";
			for(String s:args){
				execute+=" "+s;
			}
			execute=execute.substring(1);
			t.execute(new String[]{execute});
			t.exit();
			}catch(Exception e){};
		});
	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

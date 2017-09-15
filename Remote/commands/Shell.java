package commands;

import java.util.List;

import global.record.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;
import util.terminal.Terminal;

public class Shell extends CommandGenerics implements Command {

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
			List<String> out=t.getOut();
			String output="";
			for(String s:out){
				output+="\n"+s;
			}
			Lib.sendMessage(event, output.substring(1));
			}catch(Exception e){}
		});
	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

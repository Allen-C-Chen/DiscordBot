package commands;

import global.record.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;
import util.terminal.Terminal;

public class App extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		Settings.executor.execute(()->{
			try{
			Terminal t=new Terminal();
			t.execute(new String[]{"osascript -e \'tell application \""+args[0]+"\" to activate\'"});
			t.exit();
			}catch(Exception e){
				Lib.sendMessage(event, "Error occured: "+e.getMessage());
			};
		});
		

	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

package commands;

import global.record.Settings;
import macro.Hooks;
import macro.Macro;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;

public class MacroRecord extends CommandGenerics {


	@Override
	public void action(String[] args, MessageReceivedEvent event) {

		Settings.executor.execute(()->{
			try{
				Macro m=new Macro();
				m.addHooks(Hooks.MouseHook);
				m.setRecordKey(37);
				m.record(false);
				m.save(args[0]);
				Lib.sendMessage(event, "Macro Recorded "+args[0]);
			}catch(Exception e){
				Lib.sendMessage(event, "Error occured: "+e.getMessage());
			}
		});
		}
		

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

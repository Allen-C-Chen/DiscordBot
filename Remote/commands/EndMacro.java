package commands;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;

public class EndMacro extends CommandGenerics {

	public EndMacro() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			Lib.sendMessage(event, "Error occured: "+e.getMessage());
		}

	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

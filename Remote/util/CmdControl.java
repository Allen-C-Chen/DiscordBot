package util;

import java.util.HashMap;

import commands.Command;
import global.CommandParser;
import global.Main;
import global.record.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Use to hold and handle commands
 * @author Allen
 *
 */
public class CmdControl {
	public static final CommandParser parser=new CommandParser();//parse most commands
	private static HashMap<String,Command> commands=new HashMap<String,Command>();
	private static HashMap<String,Command> modCommands=new HashMap<String,Command>();
	private static HashMap<String,String> modules=new HashMap<String,String>();//used to get which module the command is from
	public static boolean parseCommands(MessageReceivedEvent event){
		if(event.getAuthor().getId().equals(Main.jda.getSelfUser().getId()))return false;
		String content=event.getMessage().getContent();
		if(content.startsWith(Settings.prefix)){
			CommandParser.CommandContainer cmd=parser.parse(content, event);
			if(CommandEnabled(event,cmd.invoke)){
				return handleCommand(parser.parse(content, event));
			}
		}
		if(content.startsWith(Settings.modPrefix)){
			return handleCommand(parser.parse(content, event));
		}
		return false;
	}
	public static void commandAction(MessageReceivedEvent event, String command, String[] args){
		if(CommandEnabled(event,command)){
			commands.get(command).action(args, event);
		}
	}
	public static void addCommand(String commandName, Command command, String Module){
		commands.put(commandName, command);
		modules.put(commandName, Module);
	}
	public static void addModCommand(String commandName,Command command){
		modCommands.put(commandName, command);
	}
	private static boolean handleCommand(CommandParser.CommandContainer cmd){
		System.out.println(cmd.invoke);
		if(commands.containsKey(cmd.invoke)&&!cmd.isModCmd){
			boolean safe=commands.get(cmd.invoke).called(cmd.args, cmd.e);
			if(safe){
				commands.get(cmd.invoke).action(cmd.args, cmd.e);
				commands.get(cmd.invoke).executed(safe, cmd.e);
			}
			else{
				commands.get(cmd.invoke).executed(safe, cmd.e);
			}
			return true;
		}
		else if(cmd.invoke.equals("help")){
			if(cmd.isModCmd){
				if(cmd.args.length>0&&modCommands.containsKey(cmd.args[0])){
					modCommands.get(cmd.args[0]).help(cmd.e);
				}
				else{
					Lib.printModHelp(cmd.e);
				}
			}
			else{
				if(cmd.args.length>0&&commands.containsKey(cmd.args[0])){
					commands.get(cmd.args[0]).help(cmd.e);
				}
				else{
					Lib.printHelp(cmd.e);
				}
			}
			return true;
		}
		return false;
	}
	private static boolean CommandEnabled(MessageReceivedEvent event, String command){
		return true;
	}
}

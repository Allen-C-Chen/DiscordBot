package util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import global.record.Log;
import global.record.Settings;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * Library of various useful methods that are used all over the place
 * @author Allen
 *
 */
public class Lib {
	//methods sending data
	/**
	 * message data for help menu
	 * @param event
	 */
	public static void printHelp(MessageReceivedEvent event){
		String msg="__***Help List***__\n"
				//+ "Use "+SaveSystem.getPrefix(event)+"help [command] to get more info on a specific command, i.e.: "+SaveSystem.getPrefix(event)+"help ping\n\n"
				+ "__**Modules**__\n"
				+ "**Core** - `ping` `invite` `info` `servers`\n"
				+ "Core commands for bot\n\n"
				+ "**Exvius** - `awaken` `equipment` `lore` `skill` `unitart` `unit`\n"
				+ "Commands to extract info from Exvicus wiki(best for GL players)\n\n"
				+ "**Reddit** - `rawaken` `requipment` `rskill` `runit`\n"
				+ "Commands to extract info from Reddit wiki(best for JP players or GL players looking for future info)\n\n"
				+ "**Salt** - `summon` `salty` `waifu` `maintenance` `gsummon`\n"
				+ "Commands that may or may not help in dealing with or evoking salt\n\n"
				+ "**Simulation** - `dailies` `lapis` `give`  `banner` `pull` `unitinventory` `dailypull` `11pull` `gpull` `unitsell` `unitawaken`\n"
				+ "Commands that are used to attempt to simulate FFBE to some degree\n\n"
				+ "**WIP** - `whale` `farmtm`\n"
				+ "Commands that are work in progress currently unimplemented\n\n"
				+ "Don't include the example brackets when using commands!\n";
				//+ "To view mod commands, use "+SaveSystem.getModPrefix(event)+"help";
		Lib.sendMessage(event, msg);
	}
	
	/**
	 * message data for mod help menu
	 * @param event
	 */
	public static void printModHelp(MessageReceivedEvent event){
		String prefix=Settings.modPrefix;
		String msg="Mod Help List\n"
				+ "The following commands are to help with control the bot on your server\n"
				+ prefix+"prefix - changes prefix used for the bo\nt"
				+ prefix+"modprefix - changes the mod prefix used for the bot\n"
				+ prefix+"toggle - toggle join messages if you don't already have a bot doing it, by default false\n"
				+ prefix+"glmodules - disable/enable a module of the bot across the server\n"
				+ prefix+"modules - disable/enable a modules on a specific channel(where you used it), will override the global setting\n"
				+ "";
		Lib.sendMessage(event, msg);
	}
	public static Message editMessage(Message message,String msg){
		return message.editMessage(msg).complete();
	}
	public static Message sendFile(MessageReceivedEvent event, Message msg, File file){
		try {
			return event.getChannel().sendFile(file, msg).complete();
		} catch (IOException e) {
			Log.logError(e);
		}
		return null;
	}
	public static Message sendFile(MessageReceivedEvent event, String msg, File file){
		Message build=null;
		if(!(msg==null||msg.equals("null"))){
			build=new MessageBuilder().append(msg).build();
		}
		return sendFile(event,build,file);
	}
	/**
	 * Sends a message which will be deleted after a period of time
	 * @param event message recieved
	 * @param msg message to send in response
	 * @param timeout time in seconds after which the message will be deleted
	 */
	public static void sendTempMessage(MessageReceivedEvent event, String msg,long timeout){
		final MessageReceivedEvent FEvent=event;
		final String FMsg=msg;
		final long FTimeout=timeout;
		Settings.executor.execute(new Runnable(){
			public void run(){
				try {
					String id=sendMessageFormated(FEvent, FMsg).getId();
					TimeUnit.SECONDS.sleep(FTimeout);
					FEvent.getChannel().deleteMessageById(id).complete();
				} catch (Exception e) {
					Log.log("ERROR", "error sending delayed message");
					Log.logShortError(e, 5);
				}
				
			}
		});
	}
	/**
	 * 
	 * Formats the message <br/>
	 * Special formatting <br/> 
	 * %userMention% mentions the user that sent the message <br/>
	 * %userName% prints name of user that sent the message <br/>
	 * %selfMention% mentions the bot<br/>
	 * %mentionMention% mentions the first mentioned user in message<br/>
	 * %mentionName% name of the first mentioned user in the message<br/>
	 * @param event message received
	 * @param msg message to send in response
	 * @return message that was sent
	 */
	public static Message sendMessageFormated(MessageReceivedEvent event,String msg){
		return Lib.sendMessage(event,Lib.FormatMessage(event,msg));
	}
	/**
	 * 
	 * Adds custom emotes to message <br/>
	 * %lapis%<br/>
	 * %SC%<br/>
	 * @param event message received
	 * @param msg message to send in response
	 * @return message that was sent
	 */
	public static Message sendMessageEmoted(MessageReceivedEvent event, String msg){
		return Lib.sendMessage(event, Lib.EmoteMessage(event, msg));
	}
	/**
	 * Formats the message <br/>
	 * Special formatting <br/> 
	 * %userMention% mentions the user that sent the message <br/>
	 * %userName% prints name of user that sent the message <br/>
	 * %selfMention% mentions the bot<br/>
	 * %mentionMention% mentions the first mentioned user in message<br/>
	 * %mentionName% name of the first mentioned user in the message<br/>
	 * <br/>
	 * Adds custom emotes to message <br/>
	 * %lapis%<br/>
	 * %SC%<br/>
	 *  @param event message received
	 * @param msg message to send in response
	 * @return message that was sent
	 */
	public static Message sendMessageWithSpecials(MessageReceivedEvent event, String msg){
		return Lib.sendMessage(event, Lib.EmoteMessage(event, Lib.FormatMessage(event, msg)));
	}
	/**
	 * generic send message, will have wrappers to fix some issues and errors in relation to sending messages
	 * @param event message received
	 * @param msg message to send someone
	 * @return message that was sent
	 */
	public static Message sendMessage(MessageReceivedEvent event,String msg){
		if(msg.length()>2000){
			Vector<String> toSend=splitMessage(msg);
			for(String s:toSend){
				sendPrivate(event,s);
			}
			if(!event.isFromType(ChannelType.PRIVATE)){
				sendMessage(event,"Message was too long. Check your DMs for response");
			}
			return null;
		}
		Message message=event.getChannel().sendMessage(msg).complete();
		return message;
}
	/**
	 * generic send message, will have wrappers to fix some issues and errors in relation to sending messages
	 * @param event message received
	 * @param msg message to send someone
	 * @return message that was sent
	 */
	public static Message sendMessage(MessageReceivedEvent event, Message msg) {
		Message message=event.getChannel().sendMessage(msg).complete();
		return message;
	}
	public static Message sendPrivate(MessageReceivedEvent event, String msg){
		event.getAuthor().openPrivateChannel().complete();//open private if it's not open
		Message message=event.getAuthor().getPrivateChannel().sendMessage(msg).complete();
		return message;
	}
	private static Vector<String> splitMessage(String msg){
		Vector<String> splitMsg=new Vector<String>();
		String[] lines=msg.split("\n");
		int length=0;
		String prep="";
		for(String s:lines){
			if(s.length()>2000){
				if(!prep.equals("")){
					splitMsg.add(prep);
					prep="";
					length=0;
				}
				splitMsg.add(s.substring(0, 2000));
				splitMsg.addAll(splitMessage(s.substring(2000)));
			}
			else{
				if(s.length()+length<2000){
					prep+="\n"+s;
					length+=s.length()+1;
				}
				else{
					splitMsg.add(prep);
					prep=s;
					length=s.length();
				}
			}
		}
		splitMsg.add(prep);
		return splitMsg;
	}
	/**
	 * Formats the message <br/>
	 * Special formatting <br/> 
	 * %userMention% mentions the user that sent the message <br/>
	 * %userName% prints name of user that sent the message <br/>
	 * %selfMention% mentions the bot<br/>
	 * %mentionMention% mentions the first mentioned user in message<br/>
	 * %mentionName% name of the first mentioned user in the message<br/>
	 * @param event message received
	 * @param msg message to send in response
	 * @return string of formatted message to send
	 */
	public static String FormatMessage(MessageReceivedEvent event,String msg){
		return msg.replace("%userMention%", event.getAuthor().getAsMention()).
				replace("%userName%", event.getAuthor().getName()).
				replace("%selfMention%", event.getJDA().getSelfUser().getAsMention()).
				replace("%mentionMention%", event.getMessage().getMentionedUsers().size()>0?event.getMessage().getMentionedUsers().get(0).getAsMention():event.getAuthor().getAsMention()).
				replace("%mentionName%",event.getMessage().getMentionedUsers().size()>0?event.getMessage().getMentionedUsers().get(0).getName():event.getAuthor().getName());
	}
	/**
	 * Adds custom emotes to message <br/>
	 * %lapis%<br/>
	 * %SC%<br/>
	 * @param event
	 * @param msg
	 * @return
	 */
	public static String EmoteMessage(MessageReceivedEvent event, String msg){
		return null;
	}
	/**
	 * Formats and send message for guild member joining <br/>
	 * Special formatting<br/>
	 * %userMention% mentions the user that joined<br/>
	 * %userName% prints name of the user<br/>
	 * %guildName% prints name of the server
	 * @param event user join event
	 * @param msg message to send in response
	 * @return message sent
	 */
	public static Message sendMessageFormated(GuildMemberJoinEvent event,String msg){
		Message message=event.getGuild().getPublicChannel().sendMessage(Lib.FormatMessage(event,msg)).complete();
		return message; 
	}
	/**
	 * Formats and send message for guild member joining <br/>
	 * Special formatting<br/>
	 * %userMention% mentions the user that joined<br/>
	 * %userName% prints name of the user<br/>
	 * %guildName% prints name of the server
	 * event user join event
	 * @param msg message to send in response
	 * @return string of formatted message to send
	 */
	public static String FormatMessage(GuildMemberJoinEvent event,String msg){
		return msg.replace("%userMention%", event.getMember().getAsMention()).
		replace("%userName%", event.getMember().getNickname()).
		replace("%guildName%",event.getGuild().getName());
	}
	/**
	 * Looks for a user either based on name string or id
	 * @param user 
	 * @param guild
	 * @return
	 */
	public static Member seachUser(String user,Guild guild){
		try{
			Long.parseLong(user);//test for nums
			for(Member m:guild.getMembers()){
				if(m.getUser().getId().equals(user));
				return m;
			}
		}catch(NumberFormatException e){
			for(Member m:guild.getMembers()){
				if(m.getUser().getName().contains(user)||m.getAsMention().contains(user)){
					return m;
				}
			}
		}
		return null;
	}
	//random utilities
	/**
	 * makes sure that string is at least x length, padding it w/ spaces
	 * @param s string to pad
	 * @param pad minimum length of string
	 * @return padded string
	 */
	public static String pad(String s, int pad){
		for(int i=s.length();i<pad;i++){
			s=s+" ";
		}
		return s;
	}
	/**
	 * if s trimmed and split is part of Character.isDigit 
	 * @param s string to check if it's a number
	 * @return if s is all numbers or not
	 */
	public static boolean isNumber(String s){
		boolean negativeStartFlag=true;
		boolean dotFlag=true;
		for(char c:s.trim().toCharArray()){
			if(!Character.isDigit(c)){
				if(!(c=='-'&&negativeStartFlag||c=='.'&&dotFlag)){
					return false;
				}
				if(c=='.'&&dotFlag){
					dotFlag=false;
				}
			}
			negativeStartFlag=false;
		}
		if(s.length()>0){
		return true;
		}
		else return false;
	}
	/**
	 * gets only number chars from a string
	 * @param s string to extract number from
	 * @return number with non digit characters removed
	 */
	public static int extractNumber(String s){
		String i="";
		for(char c:s.trim().toCharArray()){
			if(Character.isDigit(c)){
				i+=c;
			}
		}
		if(i.equals("")){
			i="-1";
		}
		return Integer.parseInt(i);
	}
	/**
	 * gets numbers(integers) from string
	 * @param s string to get numbers from
	 * @return array of all the numbers in the string
	 */
	public static int[] extractNumbers(String s){
		String i="";
		boolean number=false;//if currently indexing a number
		boolean dot=false;//decimal place
		for(char c:s.trim().toCharArray()){
			if(number){
				if(Character.isDigit(c)||(dot?false:c=='.')){
					if(c=='.'){
						dot=true;
					}
					i+=c;
				}
				else{
					i+=",";
					number=false;
					dot=false;
				}
			}
			else{
				if(Character.isDigit(c)){
					i+=c;
					number=true;
				}
			}
		}
		if(i.endsWith(",")){
			i=i.substring(0, i.length()-1);
		}
		String[] nums=i.split(",");
		int[] ints=new int[nums.length];
		for(int d=0;d<nums.length;d++){
			ints[d]=Integer.parseInt(nums[d]);
		}
		return ints;
	}
	/**
	 * if x array contains obj
	 * @param obj object if it contains
	 * @param os array of objects
	 * @return if os contains obj
	 */
	public static boolean contains(Object obj,Object[] os){
		for(Object o:os){
			if(o.equals(obj)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Adds up all the numbers in array
	 * @param numbers int array to add all the numbers for
	 * @return
	 */
	public static int Summation(int[] numbers){
		int sum=0;
		for(int i:numbers){
			sum+=i;
		}
		return sum;
	}
	/**
	 * converts a string array to 
	 * @param args
	 * @return
	 */
	public static String extract(String[] args){
		String out="";
		for(String s:args){
			out+=" "+s;
		}
		return out.substring(1);
	}
	public static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	//synchronization to avoid 2 samename files trying to get newname at the same time
	/**
	 * Gets a filename that is not currently used
	 * @param filename base filename
	 * @return filename that is not used
	 */
	public static synchronized String newFileName(String filename){
		int num=0;
		if(new File(filename).exists()){
			while(new File(filename+num).exists()){
				num++;
			}
			return filename+num;
		}
		else return filename;
	}
	
}

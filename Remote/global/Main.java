package global;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.impl.GameImpl;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import util.CmdControl;
import util.CounterPool;
import util.SpamControl;
import util.rng.RandomLibs;

import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import commands.*;
import global.record.Log;
import global.record.Settings;

public class Main {
	public static JDA jda;//JDA of bot 

	public static void main(String[] args){
		try{
			Main.startup();
			Main.setup();
		}catch(Exception e){
			Log.logError(e);
			Log.save();
		}
	}
	public static void startup() throws LoginException, IllegalArgumentException, InterruptedException{
		try{
		jda = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(Settings.token).buildBlocking();
		}catch(LoginException | RateLimitedException e){
			Log.log("System", "error on login, retrying in 5 minutes");
			TimeUnit.MINUTES.sleep(5);
			startup();
		}
		jda.setAutoReconnect(true);
		setGame(states.randomReady());
		System.out.println(states.randomReady());
	}
	public static void shutdown(){
		jda.shutdown(false);
		Log.log("status", "bot shutdown");
	}
	public static void quit(){
		jda.shutdown(true);
		Log.log("status", "Bot Quit");
		Log.save();
		System.exit(1);
	}
	/**
	 * everything that needs to be done when the JVM starts up
	 */
	public static void setup(){
		setGame(states.Loading);
		//put commands in map
		String Module="Default";
		CmdControl.addCommand("click", new Click(), Module);
		CmdControl.addCommand("shell", new Shell(), Module);
		CmdControl.addCommand("move", new Move(), Module);
		CmdControl.addCommand("sc", new SC(), Module);
		CmdControl.addCommand("shell2", new Shell2(), Module);
		CmdControl.addCommand("app", new App(), Module);
		CmdControl.addCommand("tkill", new TKill(), Module);
		CmdControl.addCommand("stroke", new Stroke(), Module);
		CmdControl.addCommand("drag", new Drag(), Module);
		CmdControl.addCommand("delay", new Delay(), Module);
		CmdControl.addCommand("cancel", new Cancel(), Module);
		CmdControl.addCommand("macro", new MacroRecord(), Module);
		CmdControl.addCommand("replay", new MacroReplay(), Module);
		CmdControl.addCommand("end", new EndMacro(), Module);
		
		//setup/build various things
	    //Log.setup();//start log thread that saves it every 6 hours
		Restarter.setup();//starts the threads that queue the bot restarting
		CounterPool.getPool().setup();//starts the thread for the counter pool
		SpamControl.setSpams();//sets the data for custom spam types
		//SaveSystem.setup();//loads all the data
		setGame(states.randomReady());//sets the game for a random state
		Delay.setup();
	}
	public static void setGame(states state){
		String game="";
		switch(state){
		case Loading:game=" the Loading Game...";
		break;
		case Maintenance:game=" undergoing maintenance";
		break;
		case Dead:game=" dead...";
		break;
		case Update:game=" updates";
		break;
		case Ready1:game=" with RNG|-!help";
		break;
		case Ready2:game="*praying to RNGesus|-!help";
		break;
		case Ready3:game=" Offline?...|-!help";
		break;
		case Ready4:game=" FFBE |-!help";
		break;
		case Ready5:game=" with a salty summoner|-!help";
		break;
		case Ready6:game=" in the salt mines|-!help";
		break;
		}
		jda.getPresence().setGame(new GameImpl(game,"null",GameType.DEFAULT));
	}	
	public static void log(String type,String msg){
		Log.log(type, msg);
	}
	/**
	 * enum for states of the bot, displayed by game it's playing
	 * @author Allen
	 *
	 */
	public static enum states{
		Loading,
		Maintenance,
		Dead,
		Update,
		Ready1,
		Ready2,
		Ready3,
		Ready4,
		Ready5,
		Ready6;
		public static int rand;
		public static states randomReady(){
			states s=RandomLibs.SelectRandom(new states[]{Ready1,Ready2,Ready3,Ready4,Ready5,Ready6});
			return s;
		}
	}
}

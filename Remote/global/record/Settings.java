package global.record;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;

/**
 * mixed bag of static settings for runtime and also used as the object representing a guild(not sure why I'm doing this) 
 * @author Allen
 *
 */
public class Settings {
	//general static settings, stored here to change easily
	public static final String token=Secrets.token;
	public static final String prefix="-";
	public static final String modPrefix="~!";
	public static final String UA="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	public static final String ExvicusURL="http://exvius.gamepedia.com/";
	public static final String ExvicusIMGURL="https://hydra-media.cursecdn.com/exvius.gamepedia.com/";
	public static final String saveSource="FFBEBotLog";//for the log
	public static final String dataSource="FFBEBotData";//for the guild based data
	public static final String preloadData="FFBEBotPreload";//for preloaded data
	public static final String overrideSource="override";//for overrides to allow other people other than owner to executethose commands
	public static final String join="User: %userMention% has joined %guildName%";//default join message
	public static final String overridePrefix="!";//prefix used for override commands
	public static final String overrideArg="-";//prefix used to denote arguments for override commands
	public static final String ownerID="206193542693912578";//ID of owner
	public static final long ID=System.currentTimeMillis();//ID for the bot based on when it was started
	public static final Semaphore upload=new Semaphore(1);//used to prevent bot from uploading 2 things at once, so that it won't end up deleting one of the images
	public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(30);//used for various threaded activities
	public static long dailyTime;
	public static HashMap<String,Settings> guilds=new HashMap<String,Settings>();//map of guilds and settings stored locally for easy access	

	
}

package commands;

import java.util.Vector;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.CmdControl;
import util.Lib;

//executes a comand after specified delay in minutes
public class Delay extends CommandGenerics implements Command {
	public static ScheduledThreadPoolExecutor delayExecute=new ScheduledThreadPoolExecutor(30);
	public static Vector<ScheduledFuture<?>> futures=new Vector<ScheduledFuture<?>>();
	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try{
			int delay=Integer.parseInt(args[0]);
			Delay.addFuture(Delay.delayExecute.schedule(()->{
				String cmd=args[1];
				String[] nargs=new String[args.length-2];
				for(int i=2;i<args.length;i++){
					nargs[i-2]=args[i];
				}
				CmdControl.commandAction(event, cmd, nargs);
			},delay,TimeUnit.MINUTES));
		}catch(Exception e){
			Lib.sendMessage(event, "Error occured: "+e.getMessage());
		}
	}
	@Override
	public void help(MessageReceivedEvent event) {
	}
	public static void addFuture(ScheduledFuture<?> future){
		futures.add(future);
	}
	public static void kill(){
		for(ScheduledFuture<?> f:futures){
			f.cancel(false);
		}
	}
	public static void setup(){
		delayExecute.setRemoveOnCancelPolicy(true);
	}

}

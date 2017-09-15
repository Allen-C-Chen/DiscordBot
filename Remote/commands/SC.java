package commands;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Lib;
import util.terminal.Terminal;

public class SC extends CommandGenerics implements Command {

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		try{
			Terminal t=new Terminal();
			t.changeDir(new File("/Users/Allen/Desktop/"));
			t.execute(new String[]{"screencapture /Users/Allen/Desktop/sc.png"});
			t.exit();
			if(!(args.length>0)){
				BufferedImage sc=null;
				try {
					sc = ImageIO.read(new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}

				Graphics g=sc.getGraphics();
				for(int i=0;i<14;i++){
					g.drawLine(0, i*100, 1800, i*100);
				}
				for(int i=0;i<18;i++){
					g.drawLine(i*100,0, i*100 ,1000 );
				}
				PointerInfo a=MouseInfo.getPointerInfo();
				Point p=a.getLocation();
				g.setColor(Color.RED);
				g.drawRect(p.x-2, p.y-2, 5, 5);
				g.drawRect(p.x-5, p.y-5, 11, 11);

				g.dispose();
				try {
					ImageIO.write(sc, "PNG", new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}
			}
			else if(args.length==1&&args[0].equals("noline")){
				BufferedImage sc=null;
				try {
					sc = ImageIO.read(new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}

				Graphics g=sc.getGraphics();
				PointerInfo a=MouseInfo.getPointerInfo();
				Point p=a.getLocation();
				g.setColor(Color.RED);
				g.drawRect(p.x-2, p.y-2, 5, 5);
				g.drawRect(p.x-5, p.y-5, 11, 11);

				g.dispose();
				try {
					ImageIO.write(sc, "PNG", new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}
			}
			else if(args.length==1&&args[0].equals("nosquare")){
				BufferedImage sc=null;
				try {
					sc = ImageIO.read(new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}

				Graphics g=sc.getGraphics();
				for(int i=0;i<14;i++){
					g.drawLine(0, i*100, 1800, i*100);
				}
				for(int i=0;i<18;i++){
					g.drawLine(i*100,0, i*100 ,1000 );
				}
				g.dispose();
				try {
					ImageIO.write(sc, "PNG", new File("/Users/Allen/Desktop/sc.png"));
				} catch (IOException e1) {}
			}
			
			Lib.sendFile(event, "screenshot", new File("/Users/Allen/Desktop/sc.png"));
			try {
				Files.delete(new File("/Users/Allen/Desktop/sc.png").toPath());
			} catch (IOException e) {
				Lib.sendMessage(event, "error"+e.toString());
			}
		}catch(Exception e){};

	}

	@Override
	public void help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}

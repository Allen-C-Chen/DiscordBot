package macro;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.io.File;

import shellUtil.Terminal;

public class ColorUtil {
	public static String getColor(int x,int y){
		return getColor(x,y,"");
		}
	public static String getColor(int x,int y,String boundApp){
		Terminal t = null;
		try{
			if(!boundApp.equals("")){//terminal is not needed to determine app position
				t = new Terminal(new File("/bin/"),"sh",50);//readwait cycle value faster so color check is faster
			}
		}catch(Exception e){}
		String c= getColor(x,y,boundApp,t);
		if(!(t==null))t.forceExit();
		return c;
	}
	public static String getColor(int x, int y, String boundApp,Terminal t){
		try {
			if(!(t==null))t.clearResponses();
			//position variables
			String[] position=new String[]{""};
			int posx=0;
			int posy=0;
			//this is to bind position to an app so the screen of it can be moved around
			if(!boundApp.equals("")&&t!=null){
				t.execute(new String[]{
						"osascript <<END",
						"tell application \"System Events\"",
						"get position of first window of application process \""+boundApp+"\"",
						"end tell",
						"END"
				});
				t.awaitResponse(1);
				position=t.getResponse().get(0).split(",");
				t.clearResponses();
			}
			if(position.length>1){
				posx=Integer.parseInt(position[0]);
				posy=Integer.parseInt(position[1]);
			}
			//-23 is due to position being get offset by 23 pixels
			//t.execute("screencapture -R" +(posx+x)+ "," +(posy+y+(posy==0?0:-23))+ ",1,1 -t bmp $TMPDIR/test.bmp && xxd -p -l 3 -s 54 $TMPDIR/test.bmp | sed 's/\\(..\\)\\(..\\)\\(..\\)/\\3\\2\\1/'");
			//t.awaitResponse(1);
			//return t.getResponse().get(0);// a bit slow
			Robot robot=new Robot();
			Color c=robot.getPixelColor(x+posx,posy+y+(posy==0?0:-23));
			System.out.println(Integer.toHexString(c.getRed())+Integer.toHexString(c.getGreen())+Integer.toHexString(c.getBlue()));
			return (Integer.toHexString(c.getRed())+Integer.toHexString(c.getGreen())+Integer.toHexString(c.getBlue()));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static boolean colorCheck(int x, int y, String color){
		return colorCheck(x,y,color,"");
	}
	public static boolean colorCheck(int x, int y, String color, String boundApp){
		Terminal t=null;
		try{
			if(!boundApp.equals("")){//terminal is not needed to determine app position
				t = new Terminal(new File("/bin/"),"sh",50);//readwait cycle value faster so color check is faster
			}
		}catch(Exception e){}
		boolean b= colorCheck(x,y,color,boundApp,t);
		if(!(t==null))t.forceExit();
		return b;
	}
	public static boolean colorCheck(int x, int y, String color, String boundApp,Terminal t){
		return (getColor(x,y,boundApp,t).equals(color));
	}
}

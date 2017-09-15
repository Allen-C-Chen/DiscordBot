package util.click;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Clicker {
	public static void click(int x, int y){
		try {
			//simulate a click at 10, 50
			Robot r = new Robot();
			r.mouseMove(x, y);
			r.mousePress(InputEvent.BUTTON1_MASK); //press the left mouse button
			Thread.sleep(100);
			r.mouseRelease(InputEvent.BUTTON1_MASK); //release the left mouse button
			//move the mouse back to the original position
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void move(int x,int y){
		try {
			//simulate a click at 10, 50
			Robot r = new Robot();
			r.mouseMove(x, y);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void drag(int x,int y, int x2, int y2){
		try{
			Robot r=new Robot();
			r.mouseMove(x,y);
			r.keyPress(KeyEvent.BUTTON1_DOWN_MASK);
			for(int i=0;i<10;i++){
				//Thread.sleep(50);
				r.mouseMove(((x2-x)/10)*i+x, ((y2-y)/10)*i+y);
			}
			//Thread.sleep(50);
			r.mouseMove(x2, y2);
			r.keyRelease(KeyEvent.BUTTON1_DOWN_MASK);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void rClick(int x,int y){
		try {
			//simulate a click at 10, 50
			Robot r = new Robot();
			r.mouseMove(x, y);
			r.mousePress(InputEvent.BUTTON2_MASK); //press the left mouse button
			Thread.sleep(100);
			r.mouseRelease(InputEvent.BUTTON2_MASK); //release the left mouse button
			//move the mouse back to the original position
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void stroke(String s){
		try{
			
			if(s.equals("back")){
				keypress(KeyEvent.VK_BACK_SPACE);
				return;
			}
			else if(s.equals("enter")){
				keypress(KeyEvent.VK_ENTER);
				return;
			}
			
			for(char c:s.toCharArray()){
				Robot r=new Robot();
				if(Character.isUpperCase(c)){
					r.keyPress(KeyEvent.SHIFT_DOWN_MASK);
					r.keyPress(stringToCode(c));
					Thread.sleep(100);
					r.keyRelease(KeyEvent.SHIFT_DOWN_MASK);
					r.keyRelease(stringToCode(c));
				}
				else{
					r.keyPress(stringToCode(c));
					Thread.sleep(100);
					r.keyRelease(stringToCode(c));
				}
			}
		}catch(Exception e){};
	}
	private static void keypress(int keycode){
		try {
			Robot r=new Robot();
			r.keyPress(keycode);
			Thread.sleep(100);
			r.keyRelease(keycode);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int stringToCode(char c){
		switch(c){
		case 'a':return KeyEvent.VK_A;
		case 'b':return KeyEvent.VK_B;
		case 'c':return KeyEvent.VK_C;
		case 'd':return KeyEvent.VK_D;
		case 'e':return KeyEvent.VK_E;
		case 'f':return KeyEvent.VK_F;
		case 'g':return KeyEvent.VK_G;
		case 'h':return KeyEvent.VK_H;
		case 'i':return KeyEvent.VK_I;
		case 'j':return KeyEvent.VK_J;
		case 'k':return KeyEvent.VK_K;
		case 'l':return KeyEvent.VK_L;
		case 'm':return KeyEvent.VK_M;
		case 'n':return KeyEvent.VK_N;
		case 'o':return KeyEvent.VK_O;
		case 'p':return KeyEvent.VK_P;
		case 'q':return KeyEvent.VK_Q;
		case 'r':return KeyEvent.VK_R;
		case 's':return KeyEvent.VK_S;
		case 't':return KeyEvent.VK_T;
		case 'u':return KeyEvent.VK_U;
		case 'v':return KeyEvent.VK_V;
		case 'w':return KeyEvent.VK_W;
		case 'x':return KeyEvent.VK_X;
		case 'y':return KeyEvent.VK_Y;
		case 'z':return KeyEvent.VK_Z;
		case '0':return KeyEvent.VK_0;
		case '1':return KeyEvent.VK_1;
		case '2':return KeyEvent.VK_2;
		case '3':return KeyEvent.VK_3;
		case '4':return KeyEvent.VK_4;
		case '5':return KeyEvent.VK_5;
		case '6':return KeyEvent.VK_6;
		case '7':return KeyEvent.VK_7;
		case '8':return KeyEvent.VK_8;
		case '9':return KeyEvent.VK_9;
		case '\"':return KeyEvent.VK_QUOTEDBL;
		case '\'':return KeyEvent.VK_QUOTE;
		case '/':return KeyEvent.VK_SLASH;
		case '\\':return KeyEvent.VK_BACK_SLASH;
		case ' ':return KeyEvent.VK_SPACE;
		case '-':return KeyEvent.VK_MINUS;
		case '+':return KeyEvent.VK_PLUS;
		case '*':return KeyEvent.VK_ASTERISK;
		case '(':return KeyEvent.VK_OPEN_BRACKET;
		case ')':return KeyEvent.VK_CLOSE_BRACKET;
		}
		return 0;
	}
	
}

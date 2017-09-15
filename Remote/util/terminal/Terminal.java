package util.terminal;

import java.io.File;
import java.io.IOException;
/**
 * Used to execute commands easily in a shell extends generic
 * @author Allen
 *
 */
public class Terminal extends shellUtil.Terminal{
	public static void main(String[] args) throws InterruptedException, IOException{
		Terminal t=new Terminal();
		t.execute(new String[]{"screencapture /Users/Allen/Desktop/test.png"});
		t.exit();
		t.PrintOut();
		t.PrintErr();
	}
	
	public Terminal() throws IOException{
		super(new File("/bin/"),"sh");
	}
	
}

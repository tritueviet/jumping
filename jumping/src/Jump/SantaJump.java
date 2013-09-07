package Jump;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

// Referenced classes of package SantaJump:
//            main
public class SantaJump extends MIDlet {
    main g;
    public SantaJump() {
    }

    protected void startApp() {
        g = null;
        try {
            g = new main(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Display.getDisplay(this).setCurrent(g);
        Thread thread = new Thread(g);
        thread.start();
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean flag) {
    }
    
}

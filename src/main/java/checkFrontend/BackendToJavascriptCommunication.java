package checkFrontend;

import checkFrontend.interfaces.ICheckCommunication;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSException;

public class BackendToJavascriptCommunication extends global.BackendToJavascriptCommunication implements ICheckCommunication {
    public BackendToJavascriptCommunication(WebEngine webEngine) {
        super(webEngine);
    }
    public void ticketAmountChanged(final int amount){
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    webEngine.executeScript("ticketAvailable("+amount+");");
                }catch (JSException ignored){}
            }
        });
    }
}

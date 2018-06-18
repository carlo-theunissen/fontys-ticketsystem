package global;

import global.interfaces.INetworkStatusUpdate;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSException;

public class BackendToJavascriptCommunication implements INetworkStatusUpdate {
    protected final WebEngine webEngine;
    private volatile boolean isOnline = true;
    private volatile int amountOffline;
    public BackendToJavascriptCommunication(WebEngine webEngine) {
        this.webEngine = webEngine;
    }


    public void online() {
        isOnline = true;
        Platform.runLater(new Runnable() {
            public void run() {
                if(isOnline) {
                    try {
                        webEngine.executeScript("online();");
                    }catch (JSException ignored){}
                }
            }
        });

    }

    public void offline(int amount) {
        amountOffline= amount;
        isOnline = false;
        Platform.runLater(new Runnable() {
            public void run() {
                if(!isOnline) {
                    try {
                        webEngine.executeScript("offline(" + amountOffline + ");");
                    }catch (JSException ignored){}
                }
            }
        });

    }
}

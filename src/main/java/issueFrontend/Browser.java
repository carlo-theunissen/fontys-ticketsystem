package issueFrontend;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    final JavascriptToBackendCommunication js;
    public Browser(String url) {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page

        webEngine.load(url);
        //add the web view to the scene
        getChildren().add(browser);



        //new ClientTicketCreator();
        js = new JavascriptToBackendCommunication(new ClientTicketCreator(new BackendToJavascriptCommunication(webEngine)));
        JSObject win
                = (JSObject) webEngine.executeScript("window");
        win.setMember("app", js);

    }
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 550;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
package global;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class Browser extends Region {

    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();
    private Object js;

    public WebEngine getWebEngine(){
        return browser.getEngine();
    }

    public Browser(String url) {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page

        webEngine.load(url);
        //add the web view to the scene
        getChildren().add(browser);

    }
    public <T> void setJsApp(T app){
        js = app;
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
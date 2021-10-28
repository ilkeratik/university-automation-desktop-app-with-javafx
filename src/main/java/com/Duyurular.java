package com;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Duyurular implements Initializable {

    @FXML
    public Button backBtn;
    @FXML
    public Button forwardBtn;
    @FXML
    private WebView webDuyuru;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webDuyuru.getEngine().load("http://yaz.muh.firat.edu.tr/tr/duyurular");

    }

    public void goBack(){
        final WebHistory history = webDuyuru.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        Platform.runLater(() ->
        {
            history.go(entryList.size() > 1
                    && currentIndex > 0
                    ? -1
                    : 0);
        });
    }
    public void goForward(){
        final WebHistory history = webDuyuru.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        Platform.runLater(() ->
        {
            history.go(entryList.size() > 1
                    && currentIndex < entryList.size() - 1
                    ? 1
                    : 0);
        });
    }
}

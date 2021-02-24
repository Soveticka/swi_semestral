package cz.osu.kip.swi.controllers;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController {

    @FXML
    private Label errorText;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize(){

    }

    public void setErrorText(String text){
        errorText.setText(text);
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

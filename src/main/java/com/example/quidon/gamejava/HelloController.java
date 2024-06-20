package com.example.quidon.gamejava;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Button button1;

    @FXML
    private Button button2;


    @FXML
    void btnClick(ActionEvent event) throws IOException {
        if (event.getSource() == button1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
            Parent lvl1Parent = loader.load();
            Scene lvl1Scene = new Scene(lvl1Parent, 600, 400);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(lvl1Scene);
            window.setResizable(false);
            window.show();
        } else if (event.getSource() == button2) {
            Platform.exit();
            System.exit(0);
        }
    }



    @FXML
    void initialize() {
    }
}


package com.example.quidon.gamejava;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Black_Scene {

    private HelloApplication helloApplication;

    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }

    @FXML
    private Button menu;

    @FXML
    private Button quit;
    private MediaPlayer mediaPlayer;

    @FXML
    void btnClick(ActionEvent event) throws IOException {
        if (event.getSource() == menu) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent lvl1Parent = loader.load();
            Scene lvl1Scene = new Scene(lvl1Parent, 600, 400);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(lvl1Scene);
            window.setResizable(false);
            window.show();

        } else if (event.getSource() == quit) {
            Platform.exit();
        }
    }

    @FXML
    private void initialize() {
        String soundFile = "src/main/resources/com/example/quidon/gamejava/sound/end.mp3";
        Media sound = new Media(new File(soundFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

        playSound();
    }

    private void playSound() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        mediaPlayer.play();
    }
}

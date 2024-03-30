package com.example.quidon.gamejava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            closeGame();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void closeGame() {

        primaryStage.close();
        System.exit(0);
    }
}

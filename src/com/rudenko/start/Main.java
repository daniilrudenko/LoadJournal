package com.rudenko.start;

import com.rudenko.models.BaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/ServerAccess.fxml"));
            primaryStage.setTitle("Вход");
            primaryStage.setScene(new Scene(root, 452, 357));
            primaryStage.setResizable(false);
            primaryStage.show();

        }

    public static void main(String[] args) {
        launch(args);
    }

}

package com.rudenko.start;

import com.rudenko.controllers.ServerAccessController;
import com.rudenko.models.BaseConnector;
import com.rudenko.models.DatabaseQueries;
import com.rudenko.models.QueriesData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/ServerAccess.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Вход");
            Scene scene = new Scene(root,452,284);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            //-------------------------------------------------------------
            // Обработка клика по кнопки закрытия на заголовке окна
            ServerAccessController controller = loader.getController();
            primaryStage.setOnCloseRequest(controller.getCloseEventHandler());

        }

    public static void main(String[] args) {
        launch(args);
    }

}

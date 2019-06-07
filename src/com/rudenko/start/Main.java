package com.rudenko.start;

import com.rudenko.controllers.ServerAccessController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;


public class Main extends Application {

    private MenuBar menuBar;
    private Menu help;

    @Override
    public void start(Stage primaryStage) throws Exception{


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/ServerAccess.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Вход");
            Scene scene = new Scene(root,452,294);
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

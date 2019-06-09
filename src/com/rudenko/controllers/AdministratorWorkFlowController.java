package com.rudenko.controllers;

import com.rudenko.models.BaseConnector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AdministratorWorkflowController {


    //-------------------------------------------
    public void addData(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage stage = null;
        Object source = actionEvent.getSource();
        if(!(source instanceof Button)){ return; }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()){
            //----------------------------------------
            case "btnFaculties":
                stage = makeModal("../fxml/FacultiesModal.fxml","Таблица факультетов",575,246,source);
                stage.show();
                break;
            //----------------------------------------
            case "btnExit":
                //------
                stage  = (Stage) ((Node) source).getScene().getWindow();
                if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
                stage.close();
        }
    }
    //-------------------------------------------

    // Обработка клика по кнопки закрытия на заголовке окна
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }
    //-------------------------------------------------------------

    public Stage makeModal(String path, String title, int width, int height, Object source) throws IOException {

        Stage stageHelper = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();
        stageHelper.setTitle(title);
        Scene scene = new Scene(root,width,height);
        stageHelper.setScene(scene);
        stageHelper.setResizable(false);
        stageHelper.initModality(Modality.WINDOW_MODAL);
        stageHelper.initOwner(((Button) source).getScene().getWindow());
        return stageHelper;
    }
}

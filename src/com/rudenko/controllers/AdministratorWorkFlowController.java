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
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdministratorWorkflowController {


    //-------------------------------------------
    public void addData(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()){
            //----------------------------------------
            case "btnFaculties":

                stage = makeModal("../fxml/FacultiesModal.fxml", "Таблица факультетов", 575,246,source);
                stage.showAndWait();
                break;
            //----------------------------------------

            case "btnDepartments":
                stage = makeModal("../fxml/DepartmentsModal.fxml","Кафедральная таблица",575,246,source);
                stage.showAndWait();
                break;

            case "btnTeachers":
                stage = makeModal("../fxml/TeachersModal.fxml","Таблица преподавателей",750,246,source);
                stage.showAndWait();
                break;

            case "btnExit":
                //------
                stage  = (Stage) ((Node) source).getScene().getWindow();
                if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
                stage.close();
                break;
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


    private Stage makeModal(String path, String title, int width, int height, Object source) throws IOException {

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

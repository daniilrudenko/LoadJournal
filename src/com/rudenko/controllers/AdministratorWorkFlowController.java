package com.rudenko.controllers;

import com.rudenko.models.BaseConnector;
import javafx.fxml.FXMLLoader;
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

                stage = makeModal("../fxml/FacultiesModal.fxml", "Таблица факультетов", 400
                        ,246,source);
                stage.showAndWait();
                break;
            //----------------------------------------

            case "btnDepartments":
                stage = makeModal("../fxml/DepartmentsModal.fxml","Кафедральная таблица",500,246,source);
                stage.showAndWait();
                break;

            case "btnTeachers":
                stage = makeModal("../fxml/TeachersModal.fxml","Таблица преподавателей",600,246,source);
                stage.showAndWait();
                break;

            case "btnAdministrators":
                stage = makeModal("../fxml/AdministratorsModal.fxml","Таблица администраторов",500,246,source);
                stage.showAndWait();
                break;

            case "btnGroups":
                stage = makeModal("../fxml/GroupsModal.fxml","Таблица групп",500,246,source);
                stage.showAndWait();
                break;

            case "btnSubjects":
                stage = makeModal("../fxml/SubjectsModal.fxml","Таблица предметов",575,246,source);
                stage.showAndWait();
                break;

            case "btnTeachersPlusDepartments":
                stage = makeModal("../fxml/TeachersPlusDepartmentsModal.fxml","Таблица преподавателей и кафедр",600,246,source);
                stage.showAndWait();
                break;

            case "btnGroupsPlusDepartments":
                stage = makeModal("../fxml/GroupsPlusDepartmentsModal.fxml","Таблица групп и кафедр",500,246,source);
                stage.showAndWait();
                break;

            case "btnSubjectsPlusDepartments":
                stage = makeModal("../fxml/SubjectsPlusDepartmentsModal.fxml","Таблица предметов и кафедр",500,246,source);
                stage.showAndWait();
                break;

            case "btnLoad":
                stage = makeModal("../fxml/LoadModal.fxml","Таблица нагрузки",575,246,source);
                stage.showAndWait();
                break;

            case "btnTimeTable":
                stage = makeModal("../fxml/CreateTimeTable.fxml","Таблица нагрузки",1200,346,source);
                stage.showAndWait();
                break;

            case "btnExit":


                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/ServerAccess.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Вход");
                Scene scene = new Scene(root,452,284);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.show();
                //-------------------------------------------
                Stage accessStage  = (Stage) ((Button) source).getScene().getWindow();
                BaseConnector.getInstance().closeConnection();
                accessStage.close();
                break;
        }
    }
    //-------------------------------------------


    // Обработка клика по кнопки закрытия на заголовке окна
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        if(BaseConnector.getInstance() != null)
            BaseConnector.getInstance().closeConnection();
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

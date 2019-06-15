package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import com.rudenko.views.MessageDialogMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sun.jvm.hotspot.debugger.linux.x86.LinuxX86CFrame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeachersPlusDepartmentsModalAddDataController {

    @FXML
    public ComboBox<String> departmentsCombo;

    @FXML
    public ComboBox<String> teachersCombo;



    private MessageDialogMaker messageDialogMaker = null;

    private TeachersPlusDepartmentsModalController.TeachersPlusDepartmentsData data;

    private ObservableList<String> observableListTeachers;

    private ObservableList<String> observableListDepartments;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;


    public TeachersPlusDepartmentsModalController.TeachersPlusDepartmentsData getData(){
        return data;
    }


    public void initialize(){

        observableListTeachers = FXCollections.observableArrayList();
        observableListDepartments = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getData("teachers");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableListTeachers.add(resultSet.getString(3).concat(" " + resultSet.getString(5)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }

        resultSet = databaseQueries.getData("departments");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableListDepartments.add(resultSet.getString(3));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        teachersCombo.setItems(observableListTeachers);
        departmentsCombo.setItems(observableListDepartments);




    }


    @FXML
    public void onButtonPressed(ActionEvent actionEvent)  {
    Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
        return;
    }

    Button clickedButton = (Button) source;

        switch (clickedButton.getId()){

        case "departmentsTeachersModalAddButtonOkay":



            if( departmentsCombo.getSelectionModel().isEmpty() || teachersCombo.getSelectionModel().isEmpty()) {
                messageDialogMaker = new MessageDialogMaker(
                        "Внимание", null,
                        "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                messageDialogMaker.show();
                break;
            }
            else {
                data = new TeachersPlusDepartmentsModalController.TeachersPlusDepartmentsData();
                List<String> list = new ArrayList<>(Arrays.asList(teachersCombo.getValue().split("\\s")));
                data.setTeachers(list.get(0));
                data.setLogin(list.get(1));
                data.setDepartments(departmentsCombo.getValue());
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
            }
        case "departmentsTeachersModalAddButtonCancel":
            Stage stage = (Stage) ((Button) source).getScene().getWindow();
            stage.close();
            break;
    }
}

}

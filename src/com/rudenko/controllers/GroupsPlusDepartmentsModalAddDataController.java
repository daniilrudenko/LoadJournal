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

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupsPlusDepartmentsModalAddDataController {


    @FXML
    public ComboBox<String> departmentsCombo;
    @FXML
    public ComboBox<String> groupsCombo;



    private MessageDialogMaker messageDialogMaker = null;

    private GroupsPlusDepartmentsModalController.GroupsPlusDepartmentsData data;

    private ObservableList<String> observableListGroups;

    private ObservableList<String> observableListDepartments;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;

    public GroupsPlusDepartmentsModalController.GroupsPlusDepartmentsData getData(){
        return data;
    }


    public void initialize(){

        observableListDepartments = FXCollections.observableArrayList();
        observableListGroups = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getData("groups");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableListGroups.add(resultSet.getString(2));
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
                System.out.println("Ошибка при считывании данных - ");
                e.printStackTrace();
            }
        }
        groupsCombo.setItems(observableListGroups);
        departmentsCombo.setItems(observableListDepartments);




    }


    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){

            case "groupsTeachersModalAddButtonOkay":



                if( departmentsCombo.getSelectionModel().isEmpty() || groupsCombo.getSelectionModel().isEmpty()) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                }
                else {
                    data = new GroupsPlusDepartmentsModalController.GroupsPlusDepartmentsData();
                    data.setDepartments(departmentsCombo.getValue());
                    data.setGroups(groupsCombo.getValue());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "groupsTeachersModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }

    }
}

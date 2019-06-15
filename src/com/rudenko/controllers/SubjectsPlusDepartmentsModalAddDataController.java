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


public class SubjectsPlusDepartmentsModalAddDataController {

    @FXML
    public ComboBox<String> departmentsCombo;

    @FXML
    public ComboBox<String> subjectsCombo;

    @FXML
    public Button subjectsTeachersModalAddButtonOkay;

    @FXML
    public Button subjectsTeachersModalAddButtonCancel;


    private MessageDialogMaker messageDialogMaker = null;

    private SubjectsPlusDepartmentsModalController.SubjectsPlusDepartmentsData data;

    private ObservableList<String> observableListSubjects;

    private ObservableList<String> observableListDepartments;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;

    public SubjectsPlusDepartmentsModalController.SubjectsPlusDepartmentsData getData(){
        return data;
    }

    public void initialize(){

        observableListSubjects = FXCollections.observableArrayList();
        observableListDepartments = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getData("subjects");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableListSubjects.add(resultSet.getString(2));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных - ");
                e.printStackTrace();
            }
        }

        resultSet = databaseQueries.getData("departments");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableListDepartments.add(resultSet.getString(3));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных.. ");
                e.printStackTrace();
            }
        }
        subjectsCombo.setItems(observableListSubjects);
        departmentsCombo.setItems(observableListDepartments);

    }


    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {

            case "subjectsTeachersModalAddButtonOkay":


                if (departmentsCombo.getSelectionModel().isEmpty() || subjectsCombo.getSelectionModel().isEmpty()) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                } else {
                    data = new SubjectsPlusDepartmentsModalController.SubjectsPlusDepartmentsData();
                    data.setDepartments(departmentsCombo.getValue());
                    data.setSubjects(subjectsCombo.getValue());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "subjectsTeachersModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
}

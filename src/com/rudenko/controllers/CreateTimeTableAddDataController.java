package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import com.rudenko.views.MessageDialogMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CreateTimeTableAddDataController {

    @FXML
    public ComboBox<String> comboTeacher;

    @FXML
    public ComboBox<String> comboGroup;

    @FXML
    public ComboBox<String> comboSubject;

    @FXML
    public ComboBox<String> comboLoad;

    @FXML
    public ComboBox<String> comboPlan;

    @FXML
    public ComboBox<String> comboDay;

    @FXML
    public ComboBox<String> comboNumber;

    @FXML
    public ComboBox<String> comboWeekType;

    ///-----------------------------------------------
    private CreateTimeTableController.TimeTableData timeTableData;
    private MessageDialogMaker messageDialogMaker = null;
    private ObservableList<String> observableList;
    private DatabaseQueries databaseQueries;
    private ResultSet resultSet;
    ///-----------------------------------------------
    public CreateTimeTableController.TimeTableData getData(){
        return timeTableData;
    }
    ///-----------------------------------------------





    public void initialize(){
        observableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getDataFromTeachersPlusDepartments();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(resultSet.getString(1).
                        concat(" |" + " " + resultSet.getString(2) +
                                " |" + " " + resultSet.getString(3)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        comboTeacher.setItems(observableList);
        //------------------------------------
        resultSet = databaseQueries.getDataFromGroupsPlusDepartments();
        observableList = FXCollections.observableArrayList();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(resultSet.getString(1).
                        concat(" " + " | " + " " + resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных - ");
                e.printStackTrace();
            }
        }
        comboGroup.setItems(observableList);
        //------------------------------------
        resultSet = databaseQueries.getDataFromSubjectsPlusDepartments();
        observableList = null;
        observableList = FXCollections.observableArrayList();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(resultSet.getString(1).
                        concat(" " + " | " + " " + resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        comboSubject.setItems(observableList);
        //------------------------------------
        resultSet = databaseQueries.getData("load");
        observableList = null;
        observableList = FXCollections.observableArrayList();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(resultSet.getString(2));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        comboLoad.setItems(observableList);
        //------------------------------------
        observableList = null;
        observableList = FXCollections.observableArrayList();
        observableList.add("1"); observableList.add("2");
        observableList.add("3");observableList.add("4");
        observableList.add("5");
        comboNumber.setItems(observableList);
        //------------------------------------
        observableList = null;
        observableList = FXCollections.observableArrayList();
        observableList.add("Понедельник"); observableList.add("Вторник");
        observableList.add("Среда");observableList.add("Четверг");
        observableList.add("Пятница");observableList.add("Суббота");
        observableList.add("Воскресенье");
        comboDay.setItems(observableList);
        //------------------------------------
        observableList = null;
        observableList = FXCollections.observableArrayList();
        observableList.add("Ставка"); observableList.add("Полставки");
        observableList.add("Почасовка");
        comboPlan.setItems(observableList);
        //------------------------------------
        observableList = null;
        observableList = FXCollections.observableArrayList();
        observableList.add("+"); observableList.add("-");
        observableList.add("+ -");
        comboWeekType.setItems(observableList);

    }



    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        String[] data = null;
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){

            case "btnOk":

                if(     comboWeekType.getSelectionModel().isEmpty() ||
                        comboTeacher.getSelectionModel().isEmpty() ||
                        comboPlan.getSelectionModel().isEmpty() ||
                        comboDay.getSelectionModel().isEmpty() ||
                        comboNumber.getSelectionModel().isEmpty() ||
                        comboLoad.getSelectionModel().isEmpty() ||
                        comboSubject.getSelectionModel().isEmpty() ||
                        comboGroup.getSelectionModel().isEmpty()) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                }
                else {
                    timeTableData = new CreateTimeTableController.TimeTableData();
                    //------------------------------------------------------------
                    data = comboTeacher.getValue().split("\\s");
                    timeTableData.setTeacher(data[3] + " " + data[5]);
                    timeTableData.setDepartments(data[1]);
                    data = comboGroup.getValue().split("\\s");
                    timeTableData.setGroup(data[5]);
                    data = comboSubject.getValue().split("\\s");
                    timeTableData.setSubject(data[5]);
                    timeTableData.setDay(comboDay.getValue());
                    timeTableData.setLoad(comboLoad.getValue());
                    timeTableData.setNumber(comboNumber.getValue());
                    timeTableData.setPlan(comboPlan.getValue());
                    timeTableData.setWeekType(comboWeekType.getValue());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "BtnCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
}

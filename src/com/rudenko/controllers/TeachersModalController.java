package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TeachersModalController {



    @FXML
    public TableView<TeachersData>   tableTeachers;


    @FXML
    public TableColumn<TeachersData,String> nameColumn;

    @FXML
    public TableColumn<TeachersData,String> surnameColumn;

    @FXML
    public TableColumn<TeachersData,String> patronymicColumn;

    @FXML
    public TableColumn<TeachersData,String> loginColumn;

    @FXML
    public TableColumn<TeachersData,String> passwordColumn;

    @FXML
    public Button btnAddTeachers;

    @FXML
    public Button btnDeleteTeachers;

    @FXML
    public Button btnTeachersCancel;

    private ObservableList<TeachersData> teachersObservableList;
    private DatabaseQueries databaseQueries;
    private TeachersData selectedItem;
    private ResultSet resultSet;
    private TeachersModalAddDataController modalAddDataController;
    private TeachersData teachersData;


    public void initialize() throws IOException {



        teachersObservableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        resultSet = databaseQueries.getData("teachers");
        while (true) {
            try {
                if (!resultSet.next()) break;
                teachersObservableList.add(new TeachersData(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));

            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableTeachers.setItems(teachersObservableList);
    }


    public void addData(TeachersData teachersData){
        Map<String,String> map = new HashMap<>();
        map.put("name",teachersData.getName());
        map.put("surname",teachersData.getSurname());
        map.put("patronymic",teachersData.getPatronymic());
        map.put("login",teachersData.getLogin());
        map.put("password",teachersData.getPassword());
        databaseQueries.insertTable(map,"teachers");
        databaseQueries.createUserTeacher(teachersData.getLogin(),teachersData.getPassword());
        teachersObservableList.add(teachersData);
        tableTeachers.refresh();
    }

    public void deleteData(TeachersData data, String value){
        int id = databaseQueries.getId("teachers","login",value);
        databaseQueries.deleteRow("teachers",id);
        databaseQueries.deleteUser(data.getLogin());
        teachersObservableList.remove(data);
        tableTeachers.getItems().remove(data);
    }


    @FXML
    public void tableTeachersButtonPressed(ActionEvent actionEvent) throws IOException {


        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {

            case "btnAddTeachers":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/TeachersModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавить данные в таблицу");
                Scene scene = new Scene(root,404,358);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                modalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //------------------------------------------------------------------------------------------------------
                if (!stageHelper.isShowing()) {
                    teachersData = modalAddDataController.getTeachersData();
                    if(teachersData!=null)
                    addData(teachersData);
                }
                break;
                //------------------------------------------------------------------------------------------------------
            case "btnDeleteTeachers":
                selectedItem = tableTeachers.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    deleteData(selectedItem, selectedItem.getLogin());
                }

                break;
            case "btnTeachersCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;


        }
    }


    protected static class TeachersData {


        private String name;

        private String surname;

        private String patronymic;

        private String login;

        private String password;


        public  TeachersData(String name, String surname, String patronymic, String login, String password) {
           this.name = name;
           this.surname = surname;
           this.patronymic = patronymic;
           this.login = login;
           this.password = password;
        }

        public TeachersData(){

        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

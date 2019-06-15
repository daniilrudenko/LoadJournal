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

public class AdministratorsModalController {

    @FXML
    public TableView<AdministratorsData> tableAdministrators;

    @FXML
    public TableColumn<AdministratorsData,String> tableAdministratorsColumnLogin;

    @FXML
    public TableColumn<AdministratorsData,String> tableAdministratorsColumnPassword;

    @FXML
    public Button btnAddAdministrators;

    @FXML
    public Button btnDeleteAdministrators;

    @FXML
    public Button btnAdministratorsCancel;

    private ObservableList<AdministratorsData> administratorsObservableList;
    private DatabaseQueries databaseQueries;
    private ResultSet resultSet;
    private AdministratorsModalAddDataController administratorsModalAddDataController;
    private AdministratorsData selectedItem;

    private AdministratorsData administratorsData;


    public void initialize(){
        databaseQueries = new DatabaseQueries();
        administratorsObservableList = FXCollections.observableArrayList();
        tableAdministratorsColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableAdministratorsColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        resultSet = databaseQueries.getData("administrators");
        while (true) {
            try {
                if (!resultSet.next()) break;
                administratorsObservableList.add(new AdministratorsData(resultSet.getString(2),resultSet.getString(3)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableAdministrators.setItems(administratorsObservableList);

    }


    public void addData(AdministratorsData administratorsData){
        Map<String,String> map = new HashMap<>();
        map.put("login",administratorsData.getLogin());
        map.put("password",administratorsData.getPassword());
        databaseQueries.insertTable(map,"administrators");
        databaseQueries.createUserAdmin(administratorsData.getLogin(),administratorsData.getPassword());
        administratorsObservableList.add(administratorsData);
        tableAdministrators.refresh();
    }

    public void deleteData(AdministratorsData administratorsData, String value){
        int id = databaseQueries.getId("administrators","login",value);
        databaseQueries.deleteRow("administrators",id);
        databaseQueries.deleteUser(administratorsData.getLogin());
        administratorsObservableList.remove(administratorsData);
        tableAdministrators.getItems().remove(administratorsData);
        tableAdministrators.refresh();
    }

    @FXML
    public void tableDepartmentsButtonPressed(ActionEvent actionEvent) throws IOException {


        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddAdministrators":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/AdministratorsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавление записи в таблицу");
                Scene scene = new Scene(root, 321, 177);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                administratorsModalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //-----------------------------------
                if (!stageHelper.isShowing()) {
                    administratorsData = administratorsModalAddDataController.getAdministratorsData();
                    if(administratorsData!=null)
                    addData(administratorsData);
                }
                //-----------------------------------
                break;

            case "btnDeleteAdministrators":
                selectedItem = tableAdministrators.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                    deleteData(selectedItem,selectedItem.getLogin());
                break;

            case "btnAdministratorsCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;

        }
    }




    public static class AdministratorsData {

        public String login;

        public String password;

        public AdministratorsData(){

        }

        public AdministratorsData(String login,String password){
            this.login = login;
            this.password = password;
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

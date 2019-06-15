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

public class GroupsModalController {


    @FXML
    public TableView<GroupsData> tableGroups;

    @FXML
    public TableColumn<GroupsData,String> groupNameColumn;

    @FXML
    public TableColumn<GroupsData,String> groupFlowColumn;

    @FXML
    public TableColumn<GroupsData,String> groupFormOfStudyingColumn;

    @FXML
    public TableColumn<GroupsData,String> groupYearColumn;

    @FXML
    public Button btnAddGroups;

    @FXML
    public Button btnDeleteGroups;

    @FXML
    public Button btnGroupsCancel;

    private ObservableList<GroupsData> observableList;
    private DatabaseQueries databaseQueries;
    private GroupsData selectedItem;
    private ResultSet resultSet;
    private GroupsModalAddDataController groupsModalAddDataController;
    private GroupsData groupsData;


    public void initialize(){
        observableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupFlowColumn.setCellValueFactory(new PropertyValueFactory<>("flow"));
        groupFormOfStudyingColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        groupYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        resultSet = databaseQueries.getData("groups");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(new GroupsData(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableGroups.setItems(observableList);
    }

    public void addData(GroupsData groupsData){
        Map<String,String> map = new HashMap<>();
        map.put("name",groupsData.getName());
        map.put("flow",groupsData.getFlow());
        map.put("year",groupsData.getYear());
        map.put("form",groupsData.getForm());
        databaseQueries.insertTable(map,"groups");
        observableList.add(groupsData);
        tableGroups.refresh();
    }


    public void deleteData(GroupsData data, String value){
        int id = databaseQueries.getId("groups","name",value);
        databaseQueries.deleteRow("groups",id);
        observableList.remove(data);
        tableGroups.getItems().remove(data);
    }



    public void groupsButtonPressed(ActionEvent actionEvent) throws IOException {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {

            case "btnAddGroups":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/GroupsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавить данные в таблицу");
                Scene scene = new Scene(root, 354, 307);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                groupsModalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //------------------------------------------------------------------------------------------------------
                if (!stageHelper.isShowing()) {
                    groupsData = groupsModalAddDataController.getGroupsData();
                    if(groupsData!= null)
                    addData(groupsData);
                }
                break;

            case "btnDeleteGroups":
                selectedItem = tableGroups.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                    deleteData(selectedItem,selectedItem.getName());
                break;

            case "btnGroupsCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;

        }
    }




    public static class GroupsData{

        private String name;
        private String flow;
        private String form;
        private String year;


        public GroupsData(){

        }

        public GroupsData(String name, String flow, String form, String year){

            this.name = name;
            this.form = form;
            this.flow = flow;
            this.year = year;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlow() {
            return flow;
        }

        public void setFlow(String flow) {
            this.flow = flow;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }



    }
}

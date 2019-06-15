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

public class SubjectsModalController {

    @FXML
    public TableView<SubjectsData> tableSubjects;

    @FXML
    public TableColumn<SubjectsData,String> tableSubjectsColumnName;

    @FXML
    public Button btnAddSubjects;

    @FXML
    public Button btnDeleteSubjects;

    @FXML
    public Button btnSubjectsCancel;

    private SubjectsData subjectsData;

    //------------------------------------------------------------
    private ObservableList<SubjectsData> subjectsObservableList;
    private DatabaseQueries databaseQueries;
    private SubjectsData selectedItem;
    private ResultSet resultSet;
    private SubjectsModalAddDataController subjectsModalAddDataController;
    //------------------------------------------------------------


    public void initialize()  {

        subjectsObservableList= FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        tableSubjectsColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultSet = databaseQueries.getData("subjects");

        while (true) {
            try {
                if (!resultSet.next()) break;
                subjectsObservableList.add(new SubjectsData(resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableSubjects.setItems(subjectsObservableList);
    }

    //------------------------------------------------------------

    public void addData(SubjectsData data){
        Map<String,String> map = new HashMap<>();
        map.put("name",data.getName());
        databaseQueries.insertTable(map,"subjects");
        subjectsObservableList.add(data);
        tableSubjects.refresh();

    }

    public void deleteData(SubjectsData data,String value){
        int id = databaseQueries.getId("subjects","name",value);
        databaseQueries.deleteRow("subjects",id);
        subjectsObservableList.remove(data);
        tableSubjects.getItems().remove(data);
    }

    //------------------------------------------------------------

    public void ButtonPressed(ActionEvent actionEvent) throws IOException {
        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "btnAddSubjects":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/SubjectsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавить данные в табилицу");
                Scene scene = new Scene(root,347,117);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                //-------------------------------------------------------------
                subjectsModalAddDataController = loader.getController();
                //-------------------------------------------------------------
                stageHelper.showAndWait();
                if(!stageHelper.isShowing()) {
                    subjectsData = subjectsModalAddDataController.getSubjectsData();
                    if(subjectsData!=null)
                    addData(subjectsData);
                }
                break;
            //---------------------------------------------------------------------
            case "btnSubjectsCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
            //---------------------------------------------------------------------
            case "btnDeleteSubjects":
                selectedItem = tableSubjects.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                    deleteData(selectedItem,selectedItem.getName());
                break;
        }
    }

    public static class SubjectsData{

        public SubjectsData(){

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public SubjectsData(String name) {
            this.name = name;
        }

        private String name;


    }
}

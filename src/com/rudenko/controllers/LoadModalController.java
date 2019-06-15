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

public class LoadModalController {

    @FXML
    public TableView<LoadData> tableLoad;
    @FXML
    public TableColumn<LoadData,String> tableLoadColumnName;

    //------------------------------------------------------------
    private ObservableList<LoadData> loadObservableList;
    private DatabaseQueries databaseQueries;
    private LoadData selectedItem;
    private ResultSet resultSet;
    private LoadModalAddDataController loadModalAddDataController;
    private LoadData loadData;
    //------------------------------------------------------------



    public void initialize()  {

        loadObservableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        tableLoadColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultSet = databaseQueries.getData("load");

        while (true) {
            try {
                if (!resultSet.next()) break;
                loadObservableList.add(new LoadData(resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableLoad.setItems(loadObservableList);
    }
    //------------------------------------------------------------

    public void addData(LoadData data){
        Map<String,String> map = new HashMap<>();
        map.put("name",data.getName());
        databaseQueries.insertTable(map,"load");
        loadObservableList.add(data);
        tableLoad.refresh();

    }

    public void deleteData(LoadData data,String value){
        int id = databaseQueries.getId("load","name",value);
        databaseQueries.deleteRow("load",id);
        loadObservableList.remove(data);
        tableLoad.getItems().remove(data);
    }

    //------------------------------------------------------------


    @FXML
    public void ButtonPressed(ActionEvent actionEvent) throws IOException {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "btnAdd":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/LoadModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавить данные в табилицу");
                Scene scene = new Scene(root,347,117);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                //-------------------------------------------------------------
                loadModalAddDataController = loader.getController();
                //-------------------------------------------------------------
                stageHelper.showAndWait();
                if(!stageHelper.isShowing()) {
                    loadData = loadModalAddDataController.getData();
                    if(loadData != null) {
                        addData(loadData);

                    }
                }
                break;
            //---------------------------------------------------------------------
            case "btnCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
            //---------------------------------------------------------------------
            case "btnDelete":
                selectedItem = tableLoad.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                    deleteData(selectedItem,selectedItem.getName());
                break;
        }
    }

    public static class LoadData{

        private String name;


        public LoadData(String name) {
            this.name = name;
        }

        public LoadData(){

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

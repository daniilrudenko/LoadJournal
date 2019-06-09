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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FacultiesModalController {

    public static String buffer = "";
    private ObservableList<FacultiesData> facultiesObservableList;
    private List<String> facultiesList;
    private DatabaseQueries databaseQueries;
    //------------------------------------------------------------

    public void initialize(){
        FacultiesData facultiesData = null;
        facultiesObservableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        tableFacultiesColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        facultiesList = databaseQueries.getData("faculties");
        for (String s : facultiesList) {
            facultiesObservableList.add(new FacultiesData(s));
        }
        tableFaculties.setItems(facultiesObservableList);
    }
    //------------------------------------------------------------

    public void addData(FacultiesData data){
        Map<String,String> map = new HashMap<>();
        map.put("name",data.getName());
        databaseQueries.insertTable(map,"faculties");
        facultiesObservableList.add(data);
        tableFaculties.setItems(facultiesObservableList);

    }

    public void deleteData(FacultiesData data,String value){
        int id = databaseQueries.getId("faculties","name",value);
        databaseQueries.deleteRow("faculties",id);
        facultiesObservableList.remove(data);
        tableFaculties.getItems().remove(data);
    }

    @FXML
    Button btnAddFaculties;

    @FXML
    Button btnChangeFaculties;

    @FXML
    Button btnDeleteFaculties;

    @FXML
    Button btnFacultiesCancel;

    @FXML
    TableColumn<FacultiesData,String> tableFacultiesColumnName;

    @FXML
    TableView<FacultiesData> tableFaculties;


    public void tableFacultiesButtonPressed(ActionEvent actionEvent) throws IOException {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "btnAddFaculties":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/FacultiesModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавить данные в таблицу факультетов: ");
                Scene scene = new Scene(root,347,117);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                stageHelper.showAndWait();
                if(!stageHelper.isShowing() && !buffer.equals("")) {
                    addData(new FacultiesData(buffer));
                }
                break;
            case "btnFacultiesCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;

            case "btnDeleteFaculties":
                FacultiesData selectedItem = tableFaculties.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                deleteData(selectedItem,selectedItem.getName());
                break;
        }
    }
    //------------------------------------------------------------


    public static class FacultiesData {




        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public FacultiesData(String data){
            name = data;
        }

        private String name;

    }
}

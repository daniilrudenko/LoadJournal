package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
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


public class FacultiesModalController {

    @FXML
    Button btnAddFaculties;

    @FXML
    Button btnDeleteFaculties;

    @FXML
    Button btnFacultiesCancel;

    @FXML
    TableColumn<FacultiesData,String> tableFacultiesColumnName;

    @FXML
    TableView<FacultiesData> tableFaculties;
    //------------------------------------------------------------
    private ObservableList<FacultiesData> facultiesObservableList;
    private DatabaseQueries databaseQueries;
    private FacultiesData selectedItem;
    private ResultSet resultSet;
    private FacultiesModalAddDataController facultiesModalAddDataController;
    //------------------------------------------------------------


    public void initialize()  {

        facultiesObservableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        tableFacultiesColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultSet = databaseQueries.getData("faculties");

        while (true) {
            try {
                if (!resultSet.next()) break;
                facultiesObservableList.add(new FacultiesData(resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableFaculties.setItems(facultiesObservableList);
    }
    //------------------------------------------------------------

    public void addData(FacultiesData data){
        Map<String,String> map = new HashMap<>();
        map.put("name",data.getName());
        databaseQueries.insertTable(map,"faculties");
        facultiesObservableList.add(data);
        tableFaculties.refresh();

    }

    public void deleteData(FacultiesData data,String value){
        int id = databaseQueries.getId("faculties","name",value);
        databaseQueries.deleteRow("faculties",id);
        facultiesObservableList.remove(data);
        tableFaculties.getItems().remove(data);
    }

    //------------------------------------------------------------



    public void tableFacultiesButtonPressed(ActionEvent actionEvent) throws IOException {


        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "btnAddFaculties":
                    Stage stageHelper = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../fxml/FacultiesModalAddData.fxml"));
                    Parent root = loader.load();
                    stageHelper.setTitle("Добавить данные в табилицу");
                    Scene scene = new Scene(root,347,117);
                    stageHelper.setScene(scene);
                    stageHelper.setResizable(false);
                    stageHelper.initModality(Modality.WINDOW_MODAL);
                    stageHelper.initOwner(((Button) source).getScene().getWindow());
                    //-------------------------------------------------------------
                    facultiesModalAddDataController = loader.getController();
                    //-------------------------------------------------------------
                    stageHelper.showAndWait();
                if(!stageHelper.isShowing()) {
                    addData(facultiesModalAddDataController.getFacultiesData());
                    System.out.println(facultiesModalAddDataController.getFacultiesData().getName());
                    tableFaculties.refresh();
                }
                break;
            //---------------------------------------------------------------------
            case "btnFacultiesCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
            //---------------------------------------------------------------------
            case "btnDeleteFaculties":
                selectedItem = tableFaculties.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                deleteData(selectedItem,selectedItem.getName());
                break;
        }
    }
    //-----------------------------------------------------------------------------


        public static class FacultiesData {

        private String name;
        FacultiesData(String data){
                name = data;
            }
        //----------------------------------------------------
         public String getName() {
            return name;
        }
         public void setName(String name) {
            this.name = name;
        }



    }
}

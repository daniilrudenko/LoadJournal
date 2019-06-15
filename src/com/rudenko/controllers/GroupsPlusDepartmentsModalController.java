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

public class GroupsPlusDepartmentsModalController {

    @FXML
    public TableView<GroupsPlusDepartmentsData> tableGroupsPlusDepartments;

    @FXML
    public TableColumn<GroupsPlusDepartmentsData,String> columnDepartments;

    @FXML
    public TableColumn<GroupsPlusDepartmentsData,String>  columnGroups;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnDel;

    @FXML
    public Button btnCancel;


    private GroupsPlusDepartmentsModalAddDataController groupsPlusDepartmentsModalAddDataController;

    private ObservableList<GroupsPlusDepartmentsData> observableList;

    private GroupsPlusDepartmentsData groupsPlusDepartmentsData;

    private GroupsPlusDepartmentsData selected;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;



    public void initialize(){

        observableList = FXCollections.observableArrayList();
        columnDepartments.setCellValueFactory(new PropertyValueFactory<>("departments"));
        columnGroups.setCellValueFactory(new PropertyValueFactory<>("groups"));
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getDataFromGroupsPlusDepartments();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(new GroupsPlusDepartmentsData(resultSet.getString(1),
                        resultSet.getString(2)));

            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableGroupsPlusDepartments.setItems(observableList);

    }



    public void addData(GroupsPlusDepartmentsData groupsPlusDepartmentsData){
        int groups_id = databaseQueries.getId("groups","name",groupsPlusDepartmentsData.getGroups());
        int departments_id = databaseQueries.getId("departments","name",groupsPlusDepartmentsData.getDepartments());
        Map<String,String> map = new HashMap<>();
        map.put("groups_id",String.valueOf(groups_id));
        map.put("departments_id",String.valueOf(departments_id));
        databaseQueries.insertTable(map,"groups_plus_departments");
        observableList.add(groupsPlusDepartmentsData);
        tableGroupsPlusDepartments.refresh();
    }

    public void deleteData(GroupsPlusDepartmentsData data,String value){
        int id = databaseQueries.getId("groups","name",value);
        int id_gpd = databaseQueries.getId("groups_plus_departments","groups_id",String.valueOf(id));
        databaseQueries.deleteRow("groups_plus_departments",id_gpd);
        observableList.remove(data);
        tableGroupsPlusDepartments.getItems().remove(data);
    }



    @FXML
    public void tableButtonPressed(ActionEvent actionEvent) throws IOException {
    Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
        return;
    }

    Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
        case "btnAdd":
            Stage stageHelper = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/GroupsPlusDepartmentsModalAddData.fxml"));
            Parent root = loader.load();
            stageHelper.setTitle("Добавление записи в таблицу");
            Scene scene = new Scene(root, 361, 177);
            stageHelper.setScene(scene);
            stageHelper.setResizable(false);
            stageHelper.initModality(Modality.WINDOW_MODAL);
            stageHelper.initOwner(((Button) source).getScene().getWindow());
            groupsPlusDepartmentsModalAddDataController = loader.getController();
            stageHelper.showAndWait();
            //-----------------------------------
            if (!stageHelper.isShowing()) {
                groupsPlusDepartmentsData = groupsPlusDepartmentsModalAddDataController.getData();
                if (groupsPlusDepartmentsData != null)
                    addData(groupsPlusDepartmentsData);
            }
            //-----------------------------------
            break;
        case "btnDel":
            selected = tableGroupsPlusDepartments.getSelectionModel().getSelectedItem();
            if(selected != null) ;
            deleteData(selected,selected.getGroups());
            break;

        case "btnCancel":
            Stage stage = (Stage) ((Button) source).getScene().getWindow();
            stage.close();
            break;
    }

}


    public static class GroupsPlusDepartmentsData{

        private String departments;
        private String groups;


        public GroupsPlusDepartmentsData(String departments, String groups) {
            this.departments = departments;
            this.groups = groups;
        }


        public GroupsPlusDepartmentsData(){

        }

        public String getDepartments() {
            return departments;
        }

        public void setDepartments(String departments) {
            this.departments = departments;
        }

        public String getGroups() {
            return groups;
        }

        public void setGroups(String groups) {
            this.groups = groups;
        }
    }
}

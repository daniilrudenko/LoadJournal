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

public class DepartmentsModalController {


    private ObservableList<DepartmentsData> departmentsObservableList;
    private DatabaseQueries databaseQueries;
    private DepartmentsData selectedItem;



    @FXML
    public TableView<DepartmentsData> tableDepartments;

    @FXML
    public TableColumn<DepartmentsData,String> tableDepartmentsColumnFaculty;

    @FXML
    public TableColumn<DepartmentsData,String> tableDepartmentsColumnName;

    @FXML
    public Button btnAddDepartments;

    private DepartmentsModalAddDataController departmentsModalAddDataController;

    @FXML
    public Button btnDeleteDepartments;

    @FXML
    public Button btnDepartmentsCancel;

    private ResultSet resultSet;

    private DepartmentsData departmentsData;




    public void initialize() {


        departmentsObservableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        tableDepartmentsColumnFaculty.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        tableDepartmentsColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultSet = databaseQueries.getDataFromDepartments();
        while (true) {
            try {
                if (!resultSet.next()) break;
                departmentsObservableList.add(new DepartmentsData(resultSet.getString(1),resultSet.getString(2)));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableDepartments.setItems(departmentsObservableList);
    }

    public void addData(DepartmentsData departmentsData){
        int id = databaseQueries.getId("faculties","name",departmentsData.getFacultyName());
        Map<String,String> map = new HashMap<>();
        map.put("faculties_id",String.valueOf(id));
        map.put("name",departmentsData.getName());
        databaseQueries.insertTable(map,"departments");
        departmentsObservableList.add(departmentsData);
        tableDepartments.refresh();
    }

    public void deleteData(DepartmentsData data,String value){
        int id = databaseQueries.getId("departments","name",value);
        databaseQueries.deleteRow("departments",id);
        departmentsObservableList.remove(data);
        tableDepartments.getItems().remove(data);
    }


    @FXML
    public void tableDepartmentsButtonPressed(ActionEvent actionEvent) throws IOException {



        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAddDepartments":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/DepartmentsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавление записи в таблицу");
                Scene scene = new Scene(root,348,177);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                departmentsModalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //-----------------------------------
                if (!stageHelper.isShowing()) {
                    departmentsData = departmentsModalAddDataController.getDepartmentsData();
                    if(departmentsData!= null)
                    addData(departmentsData);
                }
                //-----------------------------------
                break;
            case "btnDeleteDepartments":
                selectedItem = tableDepartments.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                    deleteData(selectedItem,selectedItem.getName());
                break;

            case "btnDepartmentsCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }


    public static class DepartmentsData {

        private String departmentName;

        private String facultyName;

        public DepartmentsData(){

        }

        public String getName() {
            return departmentName;
        }

        public void setName(String name) {
            departmentName = name;
        }

        public DepartmentsData(String facultyName, String name){

            this.departmentName = name;
            this.facultyName = facultyName;
        }

        public String getFacultyName() {
            return facultyName;
        }

        public void setFacultyName(String facultyName) {
            this.facultyName = facultyName;
        }
    }
}

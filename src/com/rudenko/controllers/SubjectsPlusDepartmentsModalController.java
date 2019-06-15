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

public class SubjectsPlusDepartmentsModalController {

    @FXML
    public TableView<SubjectsPlusDepartmentsData> tableSubjectsPlusDepartments;

    @FXML
    public TableColumn<SubjectsPlusDepartmentsData,String> columnDepartments;

    @FXML
    public TableColumn<SubjectsPlusDepartmentsData,String> columnSubjects;

    private SubjectsPlusDepartmentsModalAddDataController subjectsPlusDepartmentsModalAddDataController;

    private ObservableList<SubjectsPlusDepartmentsData> observableList;

    private SubjectsPlusDepartmentsData subjectsPlusDepartmentsData;

    private SubjectsPlusDepartmentsData selected;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;



    public void initialize(){

        observableList = FXCollections.observableArrayList();
        columnDepartments.setCellValueFactory(new PropertyValueFactory<>("departments"));
        columnSubjects.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getDataFromSubjectsPlusDepartments();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(new SubjectsPlusDepartmentsData(resultSet.getString(1),
                        resultSet.getString(2)));

            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableSubjectsPlusDepartments.setItems(observableList);

    }


    public void addData(SubjectsPlusDepartmentsData subjectsPlusDepartmentsData){
        int subjects_id = databaseQueries.getId("subjects","name",subjectsPlusDepartmentsData.getSubjects());
        int departments_id = databaseQueries.getId("departments","name",subjectsPlusDepartmentsData.getDepartments());
        Map<String,String> map = new HashMap<>();
        map.put("subjects_id",String.valueOf(subjects_id));
        map.put("departments_id",String.valueOf(departments_id));
        databaseQueries.insertTable(map,"subjects_plus_departments");
        observableList.add(subjectsPlusDepartmentsData);
        tableSubjectsPlusDepartments.refresh();
    }

    public void deleteData(SubjectsPlusDepartmentsData data,String value){
        int id = databaseQueries.getId("subjects","name",value);
        int id_spd = databaseQueries.getId("subjects_plus_departments","subjects_id",String.valueOf(id));
        databaseQueries.deleteRow("subjects_plus_departments",id_spd);
        observableList.remove(data);
        tableSubjectsPlusDepartments.getItems().remove(data);
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
                loader.setLocation(getClass().getResource("../fxml/SubjectsPlusDepartmentsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавление записи в таблицу");
                Scene scene = new Scene(root, 384, 177);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                subjectsPlusDepartmentsModalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //-----------------------------------
                if (!stageHelper.isShowing()) {
                    subjectsPlusDepartmentsData = subjectsPlusDepartmentsModalAddDataController.getData();
                    if (subjectsPlusDepartmentsData != null)
                        addData(subjectsPlusDepartmentsData);
                }
                //-----------------------------------
                break;
            case "btnDel":
                selected = tableSubjectsPlusDepartments.getSelectionModel().getSelectedItem();
                if(selected != null) ;
                deleteData(selected,selected.getSubjects());
                break;

            case "btnCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }

    }

    public static class SubjectsPlusDepartmentsData {

        private String departments;
        private String subjects;

        public SubjectsPlusDepartmentsData(){

        }

        public SubjectsPlusDepartmentsData(String departments, String subjects) {
            this.departments = departments;
            this.subjects = subjects;
        }

        public String getDepartments() {
            return departments;
        }

        public void setDepartments(String departments) {
            this.departments = departments;
        }

        public String getSubjects() {
            return subjects;
        }

        public void setSubjects(String subjects) {
            this.subjects = subjects;
        }
    }
}

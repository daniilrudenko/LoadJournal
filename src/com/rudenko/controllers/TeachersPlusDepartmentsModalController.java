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

public class TeachersPlusDepartmentsModalController {

    @FXML
    public TableView<TeachersPlusDepartmentsData> tableTeachersPlusDepartments;

    @FXML
    public TableColumn<TeachersPlusDepartmentsData,String> columnDepartments;

    @FXML
    public TableColumn<TeachersPlusDepartmentsData,String> columnTeachers;

    @FXML
    public TableColumn<TeachersPlusDepartmentsData,String> columnLogin;


    private TeachersPlusDepartmentsModalAddDataController teachersPlusDepartmentsModalAddDataController;

    private ObservableList<TeachersPlusDepartmentsData> observableList;

    private TeachersPlusDepartmentsData teachersPlusDepartmentsData;

    private TeachersPlusDepartmentsData selected;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;


    public void initialize(){

        observableList = FXCollections.observableArrayList();
        columnDepartments.setCellValueFactory(new PropertyValueFactory<>("departments"));
        columnTeachers.setCellValueFactory(new PropertyValueFactory<>("teachers"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getDataFromTeachersPlusDepartments();
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(new TeachersPlusDepartmentsData(resultSet.getString(2),
                        resultSet.getString(1),resultSet.getString(3)));

            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        tableTeachersPlusDepartments.setItems(observableList);

    }

    public void addData(TeachersPlusDepartmentsData departmentsData){
        int teachers_id = databaseQueries.getId("teachers","login",departmentsData.getLogin());
        int departments_id = databaseQueries.getId("departments","name",departmentsData.getDepartments());
        Map<String,String> map = new HashMap<>();
        map.put("teachers_id",String.valueOf(teachers_id));
        map.put("departments_id",String.valueOf(departments_id));
        databaseQueries.insertTable(map,"teachers_plus_departments");
        observableList.add(departmentsData);
        tableTeachersPlusDepartments.refresh();
    }

    public void deleteData(TeachersPlusDepartmentsData data,String value){
        int id = databaseQueries.getId("teachers","login",value);
        int id_tpd = databaseQueries.getId("teachers_plus_departments","teachers_id",String.valueOf(id));
        databaseQueries.deleteRow("teachers_plus_departments",id_tpd);
        observableList.remove(data);
        tableTeachersPlusDepartments.getItems().remove(data);
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
                loader.setLocation(getClass().getResource("../fxml/TeachersPlusDepartmentsModalAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавление записи в таблицу");
                Scene scene = new Scene(root, 384, 177);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                teachersPlusDepartmentsModalAddDataController = loader.getController();
                stageHelper.showAndWait();
                //-----------------------------------
                if (!stageHelper.isShowing()) {
                          teachersPlusDepartmentsData = teachersPlusDepartmentsModalAddDataController.getData();
                    if (teachersPlusDepartmentsData != null)
                        addData(teachersPlusDepartmentsData);
                }
                //-----------------------------------
                break;
            case "btnDel":
                selected = tableTeachersPlusDepartments.getSelectionModel().getSelectedItem();
                if(selected != null) ;
                    deleteData(selected,selected.getLogin());
                break;

            case "btnCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }

    }


    public static class TeachersPlusDepartmentsData{


        public TeachersPlusDepartmentsData(String teachers, String departments,String login) {
            this.teachers = teachers;
            this.departments = departments;
            this.login = login;
        }

        public TeachersPlusDepartmentsData(){
        }


        private String teachers;
        private String departments;
        private String login ;


        public String getTeachers() {
            return teachers;
        }

        public void setTeachers(String teachers) {
            this.teachers = teachers;
        }

        public String getDepartments() {
            return departments;
        }

        public void setDepartments(String departments) {
            this.departments = departments;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }
}

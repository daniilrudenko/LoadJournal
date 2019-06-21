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
import java.util.*;

public class CreateTimeTableController {

    @FXML
    public TableView<TimeTableData> timetable;
    @FXML
    public TableColumn<TimeTableData,String> columnTeacher;
    @FXML
    public TableColumn<TimeTableData,String> columnGroup;
    @FXML
    public TableColumn<TimeTableData,String> columnSubject;
    @FXML
    public TableColumn<TimeTableData,String> columnPlan;
    @FXML
    public TableColumn<TimeTableData,String>columnLoad;
    @FXML
    public TableColumn<TimeTableData,String> columnDay;
    @FXML
    public TableColumn<TimeTableData,String> columnNumber;
    @FXML
    public TableColumn<TimeTableData,String> columnTypeOfWeek;
    @FXML
    public TableColumn<TimeTableData,String> columnDepartment;
    //--------------------------------------------------------------
    private CreateTimeTableAddDataController createTimeTableAddDataController;
    private TimeTableData timeTableData;
    private TimeTableData selectedItem;
    private DatabaseQueries databaseQueries;
    private ObservableList<TimeTableData> observableList;
    private ResultSet resultSet;



    public void initialize() throws SQLException {
        observableList = FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        columnLoad.setCellValueFactory(new PropertyValueFactory<>("load"));
        columnPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        columnSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        columnTypeOfWeek.setCellValueFactory(new PropertyValueFactory<>("weekType"));
        columnTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("departments"));
        resultSet = databaseQueries.getDataFromTimeTable();

        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(new TimeTableData(
                        resultSet.getString(5),
                        resultSet.getString(8),
                        resultSet.getString(1),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }

        for(int i = 0; i < observableList.size(); i++){
            TimeTableData data = observableList.get(i);
            ResultSet resForTeacher = databaseQueries.getDataByIdFromTeachersPlDep(data.getTeacher());
            while (true) {
                try {
                    if (!resForTeacher.next()) break;
                    data.setTeacher(resForTeacher.getString(2).concat(" " + resForTeacher.getString(3)));
                    data.setDepartments(resForTeacher.getString(1));
                } catch (SQLException e) {
                    System.out.println("Ошибка при считывании данных: ");
                    e.printStackTrace();
                }
            }
        }

        for(int i = 0; i < observableList.size(); i++){
            TimeTableData data = observableList.get(i);
            ResultSet resForGroups = databaseQueries.getDataByIdFromGroupsPlDep(data.getGroup());
            while (true) {
                try {
                    if (!resForGroups.next()) break;
                    data.setGroup(resForGroups.getString(2));
                } catch (SQLException e) {
                    System.out.println("Ошибка при считывании данных: ");
                    e.printStackTrace();
                }
            }
        }

        for(int i = 0; i < observableList.size(); i++){
            TimeTableData data = observableList.get(i);
            ResultSet resForSub = databaseQueries.getDataByIdFromSubjectsPlDep(data.getSubject());
            while (true) {
                try {
                    if (!resForSub.next()) break;
                    data.setSubject((resForSub.getString(2)));
                } catch (SQLException e) {
                    System.out.println("Ошибка при считывании данных: ");
                    e.printStackTrace();
                }
            }
        }

        timetable.setItems(observableList);
    }



    public void addData(TimeTableData data){
        String [] teachersData = data.getTeacher().split("\\s");
        String loginTeacher = teachersData[1];
        int idTeacher = databaseQueries.getId("teachers","login",loginTeacher);
        int idTeachersPlus = databaseQueries.getId("teachers_plus_departments","teachers_id",String.valueOf(idTeacher));
        //------------------------------------------------
        int idGroup = databaseQueries.getId("groups","name",data.getGroup());
        int idGroupsPlus = databaseQueries.getId("groups_plus_departments","groups_id",String.valueOf(idGroup));
        //------------------------------------------------
        int idSubject = databaseQueries.getId("subjects","name",data.getSubject());
        int idSubjectPlus = databaseQueries.getId("subjects_plus_departments","subjects_id",String.valueOf(idSubject));
        //------------------------------------------------
        int idLoad = databaseQueries.getId("load","name",data.getLoad());
        //------------------------------------------------
        Map<String,String> map = new HashMap<>();
        map.put("teachers_plus_departments_id",String.valueOf(idTeachersPlus));
        map.put("groups_plus_departments_id",String.valueOf(idGroupsPlus));
        map.put("subjects_plus_departments_id",String.valueOf(idSubjectPlus));
        map.put("load_id",String.valueOf(idLoad));
        map.put("plan",data.getPlan());
        map.put("day",data.getDay());
        map.put("number",data.getNumber());
        map.put("type_of_week",data.getWeekType());
        databaseQueries.insertTable(map,"timetable");
        observableList.add(data);
        timetable.refresh();
    }

    public void deleteData(TimeTableData data) throws SQLException {
        String s = data.getTeacher();
        List<String> list = new ArrayList<>();
        list = Arrays.asList(s.split("\\s"));
        int id_teacher = databaseQueries.getId("teachers","surname",list.get(0));
        int id_teachPlus = databaseQueries.getId("teachers_plus_departments","teachers_id",String.valueOf(id_teacher));
        databaseQueries.delTimeTable(String.valueOf(id_teachPlus),data.getDay(),data.getNumber());
        observableList.remove(data);
        timetable.getItems().remove(data);
    }
    //--------------------------------------------------------------

    @FXML
    public void ButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAdd":
                Stage stageHelper = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/CreateTimeTableAddData.fxml"));
                Parent root = loader.load();
                stageHelper.setTitle("Добавление записи в таблицу");
                Scene scene = new Scene(root,441,496);
                stageHelper.setScene(scene);
                stageHelper.setResizable(false);
                stageHelper.initModality(Modality.WINDOW_MODAL);
                stageHelper.initOwner(((Button) source).getScene().getWindow());
                createTimeTableAddDataController = loader.getController();
                stageHelper.showAndWait();
                //-----------------------------------
                if (!stageHelper.isShowing()) {
                    timeTableData = createTimeTableAddDataController.getData();
                    if(timeTableData!= null)
                        addData(timeTableData);
                }
                //-----------------------------------
                break;
            case "btnDel":
                selectedItem = timetable.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                   deleteData(selectedItem);
                break;

            case "btnCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
    //--------------------------------------------------------------


    public static class TimeTableData{

        private String plan;
        private String weekType;
        private String teacher;
        private String day;
        private String number;
        private String load;
        private String subject;



        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getWeekType() {
            return weekType;
        }

        public void setWeekType(String weekType) {
            this.weekType = weekType;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getLoad() {
            return load;
        }

        public void setLoad(String load) {
            this.load = load;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public TimeTableData(String plan, String weekType, String teacher, String day, String number, String load, String subject, String group) {
            this.plan = plan;
            this.weekType = weekType;
            this.teacher = teacher;
            this.day = day;
            this.number = number;
            this.load = load;
            this.subject = subject;
            this.group = group;
        }

        public TimeTableData(){

        }



        private String group;

        public String getDepartments() {
            return departments;
        }

        public void setDepartments(String departments) {
            this.departments = departments;
        }

        private String departments;

    }
    //--------------------------------------------------------------
}

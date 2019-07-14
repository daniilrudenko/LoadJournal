package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.HideableItem;
import com.rudenko.views.LimitedTextField;
import com.rudenko.views.MessageDialogMaker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsModalAddDataController {

    @FXML
    public LimitedTextField facultiesModalAddTextField;

    @FXML
    public ComboBox<HideableItem<String>> departmentsModalAddCombobox;

    private MessageDialogMaker messageDialogMaker = null;

    private DepartmentsModalController.DepartmentsData departmentsData;

    private List<String> list;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;




    public DepartmentsModalController.DepartmentsData getDepartmentsData(){
        return departmentsData;
    }

    public void initialize(){
        facultiesModalAddTextField.setMaxLength(60);
        list = new ArrayList<>();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getData("faculties");
        while (true) {
            try {
                if (!resultSet.next()) break;
                list.add(resultSet.getString(2));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        departmentsModalAddCombobox.setEditable(true);
       departmentsModalAddCombobox = HideableItem.createComboBoxWithAutoCompletionSupport(list);

    }


    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){

            case "departmentsModalAddButtonOkay":

                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(facultiesModalAddTextField);

                if(result.equals(ControlsOpportunitiesImprover.TRUE)
                        || departmentsModalAddCombobox.getSelectionModel().isEmpty()) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                }
                else {
                    departmentsData = new DepartmentsModalController.DepartmentsData();

                    departmentsData.setName(facultiesModalAddTextField.getText());
                    departmentsData.setFacultyName(departmentsModalAddCombobox.getValue().toString());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "departmentsModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
}

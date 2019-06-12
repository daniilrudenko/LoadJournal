package com.rudenko.controllers;

import com.rudenko.models.DatabaseQueries;
import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DepartmentsModalAddDataController {

    @FXML
    public TextField facultiesModalAddTextField;

    @FXML
    public ComboBox<String> departmentsModalAddCombobox;

    @FXML
    public Button departmentsModalAddButtonOkay;

    @FXML
    public Button departmentsModalAddButtonCancel;

    private MessageDialogMaker messageDialogMaker = null;

    private DepartmentsModalController.DepartmentsData departmentsData;

    private ObservableList<String> observableList;

    private DatabaseQueries databaseQueries;

    private ResultSet resultSet;


    public DepartmentsModalController.DepartmentsData getDepartmentsData(){
        return departmentsData;
    }

    public void initialize(){
        observableList =FXCollections.observableArrayList();
        databaseQueries = new DatabaseQueries();
        resultSet = databaseQueries.getData("faculties");
        while (true) {
            try {
                if (!resultSet.next()) break;
                observableList.add(resultSet.getString(2));
            } catch (SQLException e) {
                System.out.println("Ошибка при считывании данных: ");
                e.printStackTrace();
            }
        }
        departmentsModalAddCombobox.setItems(observableList);
        departmentsModalAddCombobox.setOnAction(event -> DepartmentsModalController.comboBoxRes = (departmentsModalAddCombobox.getValue()));

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
                    departmentsData = new DepartmentsModalController.DepartmentsData
                            (departmentsModalAddCombobox.getSelectionModel().getSelectedItem(),
                                    facultiesModalAddTextField.getText());
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

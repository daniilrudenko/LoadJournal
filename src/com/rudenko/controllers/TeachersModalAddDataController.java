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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class TeachersModalAddDataController {


    @FXML
    public TextField teachersModalNameTextField;

    @FXML
    public TextField teachersModalSurnameTextField;

    @FXML
    public TextField teachersModalPatronymicTextField;

    @FXML
    public TextField teachersModalLoginTextField;

    @FXML
    public TextField teachersModalPasswordTextField;

    @FXML
    public Button teachersModalAddButtonOkay;

    @FXML
    public Button teachersModalAddButtonCancel;



    private MessageDialogMaker messageDialogMaker = null;

    private TeachersModalController.TeachersData teachersData;




    public TeachersModalController.TeachersData getTeachersData(){
        return teachersData;
    }




    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "teachersModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(teachersModalLoginTextField,
                        teachersModalNameTextField, teachersModalNameTextField,
                        teachersModalSurnameTextField, teachersModalPatronymicTextField,
                        teachersModalLoginTextField, teachersModalPasswordTextField);

                if (result.equals(ControlsOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                } else {
                    teachersData = new TeachersModalController.TeachersData();
                    teachersData.setName(teachersModalNameTextField.getText());
                    teachersData.setSurname(teachersModalSurnameTextField.getText());
                    teachersData.setPatronymic(teachersModalPatronymicTextField.getText());
                    teachersData.setLogin(teachersModalLoginTextField.getText());
                    teachersData.setPassword(teachersModalPasswordTextField.getText());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "teachersModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
        }
    }
}

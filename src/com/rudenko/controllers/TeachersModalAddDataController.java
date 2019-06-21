package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TeachersModalAddDataController {




    @FXML
    public SpacesBannedTextField teachersModalNameTextField;

    @FXML
    public SpacesBannedTextField teachersModalSurnameTextField;

    @FXML
    public SpacesBannedTextField teachersModalPatronymicTextField;

    @FXML
    public SpacesBannedTextField teachersModalLoginTextField;

    @FXML
    public SpacesBannedTextField teachersModalPasswordTextField;



    public void initialize(){
        teachersModalNameTextField.setMaxLength(60);
        teachersModalSurnameTextField.setMaxLength(60);
        teachersModalPatronymicTextField.setMaxLength(60);
        teachersModalLoginTextField.setMaxLength(60);
        teachersModalPasswordTextField.setMaxLength(60);

    }



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
                    break;
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

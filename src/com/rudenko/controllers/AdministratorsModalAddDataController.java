package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdministratorsModalAddDataController {





    @FXML
    public SpacesBannedTextField administratorsModalAddLoginTextField;

    @FXML
    public SpacesBannedTextField administratorsModalPasswordTextField;


    public void initialize(){
        administratorsModalAddLoginTextField.setMaxLength(60);
        administratorsModalPasswordTextField.setMaxLength(60);
    }


    //-------------------------------------------------

    private AdministratorsModalController.AdministratorsData administratorsData;


    //-------------------------------------------------

    public AdministratorsModalController.AdministratorsData getAdministratorsData (){
        return administratorsData;
    }

    //-------------------------------------------------



    public void onButtonPressed(ActionEvent actionEvent) {

        MessageDialogMaker messageDialogMaker = null;

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "administratorsModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(administratorsModalAddLoginTextField,administratorsModalPasswordTextField);

                if(result.equals(ControlsOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                }
                else {
                    administratorsData = new AdministratorsModalController.AdministratorsData();
                   administratorsData.setLogin(administratorsModalAddLoginTextField.getText());
                   administratorsData.setPassword(administratorsModalPasswordTextField.getText());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "administratorsModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }

    }
}

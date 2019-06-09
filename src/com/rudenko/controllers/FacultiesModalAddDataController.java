package com.rudenko.controllers;

import com.rudenko.models.ControlOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FacultiesModalAddDataController {


    @FXML
    private SpacesBannedTextField facultiesModalAddTextField;

    @FXML
    public Button facultiesModalAddButtonOkay;

    @FXML
    public Button facultiesModalAddButtonCancel;

    private MessageDialogMaker messageDialogMaker = null;

    private FacultiesModalController.FacultiesData facultiesData;



    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "facultiesModalAddButtonOkay":
                String result = ControlOpportunitiesImprover.textFieldIsEmpty(facultiesModalAddTextField);

                if(result.equals(ControlOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните поле.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                }
                else {
                    facultiesData = new FacultiesModalController.FacultiesData(facultiesModalAddTextField.getText());
                    FacultiesModalController.buffer = facultiesData.getName();
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "facultiesModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                FacultiesModalController.buffer = "";
                stage.close();
                break;
        }
    }
}

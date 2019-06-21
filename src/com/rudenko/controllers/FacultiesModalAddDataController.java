package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.LimitedTextField;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FacultiesModalAddDataController {


    @FXML
    private LimitedTextField facultiesModalAddTextField;


    //--------------------------------------------------

    private FacultiesModalController.FacultiesData facultiesData;
    //--------------------------------------------------

    public void initialize(){
        facultiesModalAddTextField.setMaxLength(60);
    }
    //--------------------------------------------------

    public FacultiesModalController.FacultiesData getFacultiesData(){
        return facultiesData;
    }
    //--------------------------------------------------

    public void onButtonPressed(ActionEvent actionEvent) {

        MessageDialogMaker messageDialogMaker;

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "facultiesModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(facultiesModalAddTextField);

                if(result.equals(ControlsOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните пустое поле", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    return;
                }
                else {
                    facultiesData = new FacultiesModalController.FacultiesData(facultiesModalAddTextField.getText());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "facultiesModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
}

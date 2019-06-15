package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.LimitedTextField;
import com.rudenko.views.MessageDialogMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SubjectsModalAddDataController {

    @FXML
    public LimitedTextField subjectsModalAddTextField;

    @FXML
    public Button subjectsModalAddButtonOkay;

    @FXML
    public Button subjectsModalAddButtonCancel;


    private SubjectsModalController.SubjectsData subjectsData;
    //--------------------------------------------------

    public void initialize(){
        subjectsModalAddTextField.setMaxLength(60);
    }
    //--------------------------------------------------

    public SubjectsModalController.SubjectsData getSubjectsData(){
        return subjectsData;
    }




    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {
        MessageDialogMaker messageDialogMaker;

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){ return; }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()){
            case "subjectsModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(subjectsModalAddTextField);

                if(result.equals(ControlsOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните пустое поле", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    return;
                }
                else {
                    subjectsData = new SubjectsModalController.SubjectsData(subjectsModalAddTextField.getText());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "subjectsModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }
    }
}

package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoadModalAddDataController {

    @FXML
    public TextField loadModalAddTextField;

    @FXML
    public Button loadModalAddButtonOkay;

    @FXML
    public Button loadModalAddButtonCancel;

    private LoadModalController.LoadData loadData;
    //--------------------------------------------------

    //--------------------------------------------------

    public LoadModalController.LoadData getData() {
        return loadData;
    }


    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {

        MessageDialogMaker messageDialogMaker;

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "loadModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(loadModalAddTextField);

                if (result.equals(ControlsOpportunitiesImprover.TRUE)) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните пустое поле", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    return;
                } else {
                    loadData = new LoadModalController.LoadData(loadModalAddTextField.getText());
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

package com.rudenko.controllers;

import com.rudenko.models.ControlsOpportunitiesImprover;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class GroupsModalAddDataController {

    @FXML
    public SpacesBannedTextField groupsModalNameTextField;

    @FXML
    public SpacesBannedTextField groupsModalFlowTextField;

    @FXML
    public SpacesBannedTextField groupsModalYearTextField;


    @FXML
    public ComboBox<String> groupsComboBoxForm;

    private ObservableList<String> list = FXCollections.observableArrayList();

    private MessageDialogMaker messageDialogMaker = null;

    private GroupsModalController.GroupsData groupsData;


    public GroupsModalController.GroupsData getGroupsData(){
        return groupsData;
    }


    public void initialize(){
        groupsModalFlowTextField.setMaxLength(60);
        groupsModalNameTextField.setMaxLength(60);
        groupsModalYearTextField.setMaxLength(60);
        list.add("Очно-заочная");
        list.add("Очная");
        list.add("Заочная");
        groupsComboBoxForm.setItems(list);
    }

    public void onButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "groupsModalAddButtonOkay":
                String result = ControlsOpportunitiesImprover.textFieldIsEmpty(groupsModalFlowTextField,
                        groupsModalNameTextField,
                        groupsModalYearTextField);

                if (result.equals(ControlsOpportunitiesImprover.TRUE) ||
                        groupsComboBoxForm.getSelectionModel().isEmpty()) {
                    messageDialogMaker = new MessageDialogMaker(
                            "Внимание", null,
                            "Пожалуйста, заполните все данные.", Alert.AlertType.WARNING);
                    messageDialogMaker.show();
                    break;
                } else {
                    groupsData = new GroupsModalController.GroupsData();
                    groupsData.setName(groupsModalNameTextField.getText());
                    groupsData.setFlow(groupsModalFlowTextField.getText());
                    groupsData.setForm(groupsComboBoxForm.getSelectionModel().getSelectedItem());
                    groupsData.setYear(groupsModalYearTextField.getText());
                    Stage stage = (Stage) ((Button) source).getScene().getWindow();
                    stage.close();
                    break;
                }
            case "groupsModalAddButtonCancel":
                Stage stage = (Stage) ((Button) source).getScene().getWindow();
                stage.close();
                break;
        }

    }
}

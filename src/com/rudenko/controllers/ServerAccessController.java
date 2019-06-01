package com.rudenko.controllers;

import com.rudenko.models.BaseConnector;
import com.rudenko.models.ControlOpportunitiesImprover;
import com.rudenko.views.LimitedTextField;
import com.rudenko.views.MessageDialogMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



public class ServerAccessController  {

    @FXML
    private LimitedTextField textField_URL;        // Поле ввода URL

    @FXML
    private LimitedTextField textField_Port;       // Поле ввода значения порта

    @FXML
    private LimitedTextField textField_User;       // Поле ввода пользователя бд

    @FXML
    private LimitedTextField textField_Password;   // Поле ввода пароля
    //-------------------------------------------------------------

    //Инициализация

    public void initialize(){

        // Установка значения текстовых полей

        textField_URL.setMaxLength(40);
        textField_Port.setMaxLength(40);
        textField_User.setMaxLength(40);
        textField_Password.setMaxLength(40);
    }

    // Закрытие окна, при нажатии "Выход"

    @FXML
    private void onClose(ActionEvent actionEvent){
            Node source = (Node)  actionEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
            stage.close();
    }
    //-------------------------------------------------------------

    // Соеденение с сервером. Нажатие кнопки "Применить"

    @FXML
    private void onAccept() {

        boolean resultConnection = false;
        //-------------------------------
        String resultForTextFieldIsEmpty;
        //-------------------------------
        String url      = null;
        String userName = null;
        String password = null;
        String port     = null;
        //-------------------------------
        MessageDialogMaker messageDialogMaker = null;
        //-------------------------------------------
        resultForTextFieldIsEmpty = ControlOpportunitiesImprover.textFieldIsEmpty(textField_URL,textField_Port,
                textField_Password,textField_User);
        //-------------------------------------------
        if(resultForTextFieldIsEmpty.equals(ControlOpportunitiesImprover.TRUE)){
             messageDialogMaker = new MessageDialogMaker(
                    "Внимание", null,
                    "Пожалуйста, заполните все поля", Alert.AlertType.WARNING);
            messageDialogMaker.show();
            return;
        }
        //-------------------------------------------
        url       = textField_URL.getText();
        port      = textField_Port.getText();
        userName  = textField_User.getText();
        password  = textField_Password.getText();
        //-------------------------------------------
        resultConnection = BaseConnector.getInstance().createConnection(url,port,userName,password);
        //-------------------------------------------
        if(resultConnection){
            messageDialogMaker = new MessageDialogMaker(
                    "Внимание", null,
                    "Успешное соеденение с сервером!", Alert.AlertType.INFORMATION);
            messageDialogMaker.show();
        }
        else {
                messageDialogMaker = new MessageDialogMaker(
                        "Внимание", null,
                        "Не удалось подключиться к серверу.\n" +
                                "Проверьте соеденение с интернетом, корректность ввода данных" +
                                " или конфигурацию сервера", Alert.AlertType.ERROR);
                messageDialogMaker.show();
        }
    }

    //-------------------------------------------------------------
}

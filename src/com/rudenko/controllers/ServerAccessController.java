package com.rudenko.controllers;

import com.rudenko.models.BaseConnector;
import com.rudenko.models.ControlOpportunitiesImprover;
import com.rudenko.models.FileWorker;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ServerAccessController  {

    @FXML
    private SpacesBannedTextField textField_URL;        // Поле ввода URL

    @FXML
    private SpacesBannedTextField textField_Port;       // Поле ввода значения порта

    @FXML
    private SpacesBannedTextField textField_User;       // Поле ввода пользователя бд

    @FXML
    private SpacesBannedTextField textField_Password;   // Поле ввода пароля
    //-------------------------------------------------------------
    private FileWorker fileWorker;
    private List<String> serverUserDatas;

    //Инициализация

    public void initialize(){

        // Установка значения текстовых полей

        serverUserDatas = new ArrayList<>();

        fileWorker = new FileWorker("ServerUserAuthorization.txt");
        if(!fileWorker.doesFileExist()) fileWorker.createFile();
        serverUserDatas = fileWorker.fileRead();
        if(serverUserDatas.size() == 4)
        {
        textField_URL.setText(serverUserDatas.get(0));
        textField_Port.setText(serverUserDatas.get(1));
        textField_User.setText(serverUserDatas.get(2));
        textField_Password.setText(serverUserDatas.get(3));
        }

        textField_URL.setMaxLength(60);
        textField_Port.setMaxLength(60);
        textField_User.setMaxLength(60);
        textField_Password.setMaxLength(60);
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

        
        fileWorker.fileWrite(url, port, userName,password);
    }
    //-------------------------------------------------------------

    // Обработка клика по кнопки закрытия на заголовке окна
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
        }
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }
    //-------------------------------------------------------------
}

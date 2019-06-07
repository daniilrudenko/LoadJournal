package com.rudenko.controllers;

import com.rudenko.models.BaseConnector;
import com.rudenko.models.ControlOpportunitiesImprover;
import com.rudenko.models.DatabaseQueries;
import com.rudenko.models.FileWorker;
import com.rudenko.views.MessageDialogMaker;
import com.rudenko.views.PasswordTextField;
import com.rudenko.views.SpacesBannedTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ServerAccessController  {


    @FXML
    private SpacesBannedTextField textField_URL;        // Поле ввода URL

    @FXML
    private SpacesBannedTextField textField_User;       // Поле ввода пользователя бд

    @FXML
    private PasswordTextField textField_Password;       // Поле ввода пароля
    //-------------------------------------------------------------
    private FileWorker fileWorker;
    //-------------------------------------------------------------
    private String passwordFromFile;


    //Инициализация


    public void initialize(){

        List<String> serverUserData = new ArrayList<>();

        // Обращение к файлу. Если не существует - создание. Далее - чтение

        fileWorker   = new FileWorker("ServerUserAuthorization.txt");
        if(!fileWorker.doesFileExist()) fileWorker.createFile();
        serverUserData = fileWorker.fileRead();
        //-------------------------------------------------

        // Заполнение данными из файла если данные уже существовали
        
        if(serverUserData.size() == 2) {
        textField_URL.setText(serverUserData.get(0));
        textField_User.setText(serverUserData.get(1));

        }

        //-------------------------------------------------

        // Установка значения текстовых полей
        textField_URL.setMaxLength(60);
        textField_User.setMaxLength(60);
        textField_Password.setMaxLength(60);
    }
    //-------------------------------------------------------------

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
    private void onAccept(ActionEvent actionEvent) throws IOException {

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
        resultForTextFieldIsEmpty = ControlOpportunitiesImprover.textFieldIsEmpty(textField_URL,
                textField_Password,textField_User,textField_Password);
        //-------------------------------------------
        if(resultForTextFieldIsEmpty.equals(ControlOpportunitiesImprover.TRUE)){
             messageDialogMaker = new MessageDialogMaker(
                    "Внимание", null,
                    "Пожалуйста, заполните все поля.", Alert.AlertType.WARNING);
            messageDialogMaker.show();
            return;
        }
        //-------------------------------------------
        url       = textField_URL.getText();
        userName  = textField_User.getText();
        password  = textField_Password.getValueOfTextField();

        //-------------------------------------------
        resultConnection = BaseConnector.getInstance().createConnection(url,userName,password);
        //-------------------------------------------
        if(resultConnection){
            messageDialogMaker = new MessageDialogMaker(
                    "Внимание", null,
                    "Успешное соеденение с сервером!", Alert.AlertType.INFORMATION);

            fileWorker.fileWrite(url, userName);
            messageDialogMaker.show();
            //-------------------------------------------
            Node  source = (Node)  actionEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
            /*
            Stage adminStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/AdministratorWorkflow.fxml"));
            Parent root = loader.load();
            adminStage.setTitle("Администратор");
            Scene scene = new Scene(root,452,357);
            adminStage.setScene(scene);
            adminStage.setResizable(false);
            // close the dialog.
             */
        }
        else {
                messageDialogMaker = new MessageDialogMaker(
                        "Внимание", null,
                        "Не удалось подключиться к серверу.\n" +
                                "Проверьте соеденение с интернетом, корректность введенных данных данных" +
                                " или конфигурацию сервера.", Alert.AlertType.ERROR);
                messageDialogMaker.show();
        }

    }
    //-------------------------------------------------------------

    // Обработка клика по кнопки закрытия на заголовке окна
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        if(BaseConnector.getInstance() != null) BaseConnector.getInstance().closeConnection();
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }
    //-------------------------------------------------------------
}

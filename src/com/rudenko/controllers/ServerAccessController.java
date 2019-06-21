package com.rudenko.controllers;

import com.rudenko.models.*;
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
import javafx.scene.control.Button;
import javafx.stage.Modality;
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
    private FileWorker fileWorkerUserData;
    private FileWorker fileWorkerDatabaseFlag;
    //-------------------------------------------------------------
    private boolean databaseFlag = false;
    private DatabaseQueries databaseQueries ;

    //Инициализация


    public void initialize() throws IOException {

        // Проверка на существование базы данных
        fileWorkerDatabaseFlag = new FileWorker("DatabaseFlag.txt");
        databaseFlag = fileWorkerDatabaseFlag.doesFileExist();
        //-------------------------------------------------
        // Обращение к файлу. Если не существует - создание. Далее - чтение
        List<String> serverUserData = new ArrayList<>();
        fileWorkerUserData = new FileWorker("ServerUserAuthorization.txt");
        if(!fileWorkerUserData.doesFileExist()) fileWorkerUserData.createFile();
        serverUserData = fileWorkerUserData.fileRead();
        //-------------------------------------------------
        // Заполнение данными из файла если данные уже существовали
        if(serverUserData.size() == 2 || serverUserData.size() == 3) {
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

        //-------------------------------
        MessageDialogMaker messageDialogMaker = null;
        //-------------------------------------------
        resultForTextFieldIsEmpty = ControlsOpportunitiesImprover.textFieldIsEmpty(textField_URL, textField_User,textField_Password);
        //-------------------------------------------
        if(resultForTextFieldIsEmpty.equals(ControlsOpportunitiesImprover.TRUE)){
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
        if(!databaseFlag) {
            BaseConnector.getInstance().createConnection(url,userName,password,"");
            databaseQueries = new DatabaseQueries();
            databaseQueries.createDatabase(ServerAuthorizationData.DATABASE_NAME);
            BaseConnector.getInstance().closeConnection();
            databaseQueries = null;
        }
        resultConnection = BaseConnector.getInstance().createConnection(url,userName,password,ServerAuthorizationData.DATABASE_NAME);
        //-------------------------------------------
        if(resultConnection){
            messageDialogMaker = new MessageDialogMaker(
                    "Внимание", null,
                    "Успешное соеденение с сервером!", Alert.AlertType.INFORMATION);

            fileWorkerUserData.fileWrite(url, userName);
            //-------------------------------------------
            DatabaseQueries databaseQueries = new DatabaseQueries();
            if(!databaseFlag) {
                databaseQueries.createTable("faculties",QueriesData.CREATE_FACULTIES);
                databaseQueries.createTable("departments",QueriesData.CREATE_DEPARTMENTS);
                databaseQueries.createTable("teachers",QueriesData.CREATE_TEACHERS);
                databaseQueries.createTable("administrators",QueriesData.CREATE_ADMINISTRATORS);
                databaseQueries.createTable("teachers_plus_departments",QueriesData.CREATE_TEACHERS_PLUS_DEPARTMENTS);
                databaseQueries.createTable("groups",QueriesData.CREATE_GROUPS);
                databaseQueries.createTable("groups_plus_departments",QueriesData.CREATE_GROUPS_PLUS_DEPARTMENTS);
                databaseQueries.createTable("subjects",QueriesData.CREATE_SUBJECTS);
                databaseQueries.createTable("subjects_plus_departments",QueriesData.CREATE_SUBJECTS_PLUS_DEPARTMENTS);
                databaseQueries.createTable("load",QueriesData.CREATE_LOAD);
                databaseQueries.createTable("timetable",QueriesData.CREATE_TIMETABLE);
                fileWorkerDatabaseFlag.createFile();
            }
            messageDialogMaker.show();
            //-------------------------------------------
            //-------------------------------------------
            Stage stageHelper = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/AdministratorWorkFlow.fxml"));
            Parent root = loader.load();
            stageHelper.setTitle("Панель администратора");
            Scene scene = new Scene(root,699,500);
            stageHelper.setScene(scene);
            stageHelper.setResizable(false);
            stageHelper.initModality(Modality.WINDOW_MODAL);
            AdministratorWorkflowController controller = loader.getController();
            stageHelper.setOnCloseRequest(controller.getCloseEventHandler());
            stageHelper.show();
            //-------------------------------------------
            Node  source = (Node)  actionEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else {
                messageDialogMaker = new MessageDialogMaker(
                        "Внимание", null,
                        "Не удалось подключиться к серверу.\n" +
                                "Проверьте соеденение с интернетом, корректность введенных данных" +
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

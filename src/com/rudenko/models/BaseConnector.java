package com.rudenko.models;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BaseConnector {

    private static BaseConnector instance;          // Объект Singleton
    private Connection connection;
    //----------------------------------------------
    private  String url;
    private  String port;
    private  String userName;
    private  String password;
    //----------------------------------------------
    private boolean wasDriverInitialized;
    //----------------------------------------------

    // Геттеры
    public String getUrl() {
        return url;
    }

    public String getPort() { return port; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    //----------------------------------------------

    // Загрузка драйвера

    private boolean initialize(){

        if(!wasDriverInitialized){
            try {
                Class.forName("org.postgresql.Driver");
                System.out.println("Драйвер \"org.postgresql.Driver\" загружен ");
                wasDriverInitialized = true;

            } catch (ClassNotFoundException ClassNotFoundEx) {
                wasDriverInitialized = false;
                System.out.println("Драйвер \"org.postgresql.Driver\" не обнаружен.\nДобавьте драйвер в директорию проекта ");
                ClassNotFoundEx.printStackTrace();
            }
        }
        return wasDriverInitialized;
    }

    private BaseConnector() {
        // Приватный конструктор для реализации Singleton
    }


    //----------------------------------------------

    public boolean createConnection(String url, String port, String userName, String password){

        boolean result = false;

        if(initialize()) {

            this.url  = "jdbc:postgresql://";
            this.port = ":";


            this.url  += url;
            this.port += port;
            this.userName = userName;
            this.password = password;


            this.port += "/";
            this.url += this.port;


            closeConnection();

            try {

                this.connection = DriverManager.getConnection(this.url, this.userName,this.password);
                System.out.println("Успешное соеденение с сервером ");
                result = true;

            } catch (SQLException exc) {
                result = false;
                System.out.println("Не удалось подключиться к серверу: ");
                exc.printStackTrace();
            }
        }
        return result;
    }
    //----------------------------------------------

    public static BaseConnector getInstance() {

        if (instance == null) instance = new BaseConnector();
        return instance;
    }
    //----------------------------------------------

    public void closeConnection() {

        try {
            if( connection != null && !connection.isClosed()){
                connection.close();

            }
        }catch (SQLException exc){
            System.out.println("Ошибка при закрытии соеденения: ");
            exc.printStackTrace();
        }
    }
}


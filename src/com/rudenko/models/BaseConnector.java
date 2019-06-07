package com.rudenko.models;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BaseConnector {

    private static BaseConnector instance;          // Объект Singleton
    private Connection connection;
    //----------------------------------------------
    private UserAuthorizationData   user;
    //----------------------------------------------
    public UserAuthorizationData getUser() {
        return user;
    }

    public ServerAuthorizationData getServer() {
        return server;
    }

    public Connection getConnection() {
        return connection;
    }

    private ServerAuthorizationData server;
    //----------------------------------------------
    private boolean wasDriverInitialized;
    //----------------------------------------------
    private final String JDBC_PROTOCOL = "jdbc:postgresql://";
    private final String DRIVER_NAME   = "org.postgresql.Driver";
    //----------------------------------------------


    // Загрузка драйвера

    private boolean initialize(){

        if(!wasDriverInitialized){


            try {
                Class.forName(DRIVER_NAME);
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
    //----------------------------------------------

    private BaseConnector() {
        server = new ServerAuthorizationData(JDBC_PROTOCOL,":");
        user   = new UserAuthorizationData();
    }
    //----------------------------------------------

    public boolean createConnection(String url, String userName, String password){

        boolean result = false;

        if(initialize()) {
            //----------------------------------
            server.setUrl(JDBC_PROTOCOL);
            //----------------------------------
            server.setUrl(server.getUrl().concat(url));
            //----------------------------------
            user.setUserName(userName);
            //----------------------------------
            user.setPassword(password);
            //----------------------------------

            closeConnection();

            //----------------------------------
            try {

                this.connection = DriverManager.getConnection(server.getUrl(), user.getUserName(),user.getPassword());
                System.out.println("Успешное соеденение с сервером ");
                result = true;

            } catch (SQLException exc) {
                result = false;
                System.out.println("Не удалось подключиться к серверу: ");
                exc.printStackTrace();
                System.out.println(exc.getSQLState());
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

    public boolean doesConnectionExist(){
        boolean exist = false;
        exist = connection != null;
        return exist;
    }

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


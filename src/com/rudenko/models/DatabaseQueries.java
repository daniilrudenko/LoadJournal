package com.rudenko.models;

import java.sql.*;

public class DatabaseQueries {

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet;
    public static final String databaseName = "university";
    //------------------------------------------------------------------------------------------------

    // Создание базы данных

    public void createDatabase(){

        if(!BaseConnector.getInstance().doesConnectionExist()){
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        }
        else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create database " + databaseName);
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
        }
    }
    //------------------------------------------------------------------------------------------------
    public void doesDatabaseExist(){
        if(!BaseConnector.getInstance().doesConnectionExist()){
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        }
        else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                   // statement.execute("SELECT datname FROM pg_catalog.pg_database WHERE lower(datname) = lower('dbname')" + databaseName);
                } catch (SQLException e) {
                    System.out.println("Бд не существует ");
                    e.printStackTrace();
                }
            }
        }
    }

    //Создание таблицы администраторы

    public void createTableAdministrators() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists administrators(" +
                                                "id serial primary key , " +
                                                "login character varying (30)," +
                                                "password character varying (30)" +
                                                ");");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------
}

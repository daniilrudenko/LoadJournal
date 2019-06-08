package com.rudenko.models;

import com.rudenko.controllers.ServerAccessController;
import sun.tools.jconsole.Tab;

import java.sql.*;
import java.util.*;

public class DatabaseQueries {

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet;
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //-----------------------------------Создание базы данных, удаление-------------------------------------------------


    // Создание базы данных

    public void createDatabase(String databaseName) {


        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
            try {
                statement.executeUpdate("create database " + databaseName);
                System.out.println("База данных " + "\"" + databaseName + "\" " + "создана.");
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос");
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    public void deleteDatabase(String databaseName) {


        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
            try {
                statement.executeUpdate("drop database " + databaseName);
                System.out.println("База данных " + "\"" + databaseName + "\" " + "удалена.");
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос");
                e.printStackTrace();
            }
        }
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------Создание таблиц------------------------------------------------------

    public void createTable(String tableName, String query){
        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
            try {
                statement.executeUpdate(query);
                System.out.println("Таблица " + "\"" + tableName + "\" " + "создана.");
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос: ");
                e.printStackTrace();
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------Удаление таблиц и данных--------------------------------------------------

    public void deleteTable(String tableName) {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {

            try {
                preparedStatement = BaseConnector.getInstance().getConnection().prepareStatement("drop table " + tableName);
                preparedStatement.execute();
                System.out.println("Таблица " + "\"" + tableName + "\" " + "удалена.");
            } catch (SQLException e) {
                System.out.println("Проблема с запросом: ");
                e.printStackTrace();
            }
        }
    }

    public void deleteAllTableValues(String tableName) {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            try {
                preparedStatement = BaseConnector.getInstance().getConnection().prepareStatement("delete from " + tableName + "");
                preparedStatement.execute();
                System.out.println("Все данные из таблицы " + "\"" + tableName + "\" " + "удалены.");
            } catch (SQLException e) {
                System.out.println("Проблема с запросом: ");
                e.printStackTrace();
            }
        }
    }


    public void deleteRow(String tableName, int number) {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {

            try {
                preparedStatement = BaseConnector.getInstance().getConnection().prepareStatement("delete from " + tableName + " where id = ?");
                preparedStatement.setInt(1, number);
                preparedStatement.execute();
                System.out.println("Строка из таблицы" + "\"" + tableName + "\"" + "," + " " + "где id = " + "\"" + tableName + "\" " + "-" + "удалена.");
            } catch (SQLException e) {
                System.out.println("Проблема с запросом: ");
                e.printStackTrace();
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------Вставка в таблицы------------------------------------------------------


    public void insertTable(Map<String,String> map , String tableName) {


        Set<String> listKeys = map.keySet();
        Collection<String> listValues= map.values();
        String fieldName = "(";
        String fieldValues = "(\'";
        List<String> keys = new ArrayList<>(listKeys);
        List<String> values = new ArrayList<>(listValues);
        Collections.reverse(keys);
        Collections.reverse(values);
        //---------------------------------------------------
        for(int i = 0; i< keys.size(); i++){
            if(i + 1 == keys.size()){
                fieldName = fieldName.concat(keys.get(i) + ")");
                fieldValues = fieldValues.concat(values.get(i) + "\')");
                break;
            }
            fieldName = fieldName.concat(keys.get(i));
            fieldName = fieldName.concat(",");
            fieldValues = fieldValues.concat(values.get(i));
            fieldValues = fieldValues.concat("\',\'");

        }
        //---------------------------------------------------
        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
            try {
                statement.executeUpdate("insert into " + tableName + " " + fieldName  + " values " +
                      fieldValues);
                System.out.println("Вставка данных в таблицу " + "\"" + tableName + "\" " + " прошла успешно");
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос: ");
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------Вывод данных из таблиц----------------------------------------------------

    public List<String> getData(String tableName) {

        List<String> listTimes = new ArrayList<>();

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();

                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
            try {
                resultSet = statement.executeQuery("select * from " + tableName);
                System.out.println("Данные из таблицы " + tableName + " получены");
            } catch (SQLException e) {
                System.out.println("Не удалось получить данные из таблицы: ");
                e.printStackTrace();
            }
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    listTimes.add(resultSet.getString(2));
                } catch (SQLException e) {
                    System.out.println("Ошибка при считывании данных: ");
                    e.printStackTrace();
                }
            }
        }
        return listTimes;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------Вывод id---------------------------------------------------------

    public void getId(String tableName, String field, String value) {
        int res = -1;
        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
            try {
                resultSet = statement.executeQuery("select " + tableName + ".id from " + tableName + " where " + tableName + "." + field + "= " + "\'" + value + "\'");
                while (resultSet.next())
                    res = resultSet.getInt(1);
                System.out.println("ID получен из - " + "faculties");
            } catch (SQLException e) {
                System.out.println("Не удалось выполнить запрос: ");
                e.printStackTrace();
            }
        }
    }

}

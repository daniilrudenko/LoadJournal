package com.rudenko.models;

import java.sql.*;

public class DatabaseQueries {

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet;
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //------------------------Создание базы данных, удаление. Таблица администраторов-----------------------------------


    // Создание базы данных

    public void createDatabase(String databaseName){

        if(!BaseConnector.getInstance().doesConnectionExist()){
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        }
        else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create database " + databaseName);
                    System.out.println("База данных " + "\"" + databaseName + "\" " + "создана.");
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
        }
    }
    //------------------------------------------------------------------------------------------------
    public void deleteDatabase(String databaseName){

        if(!BaseConnector.getInstance().doesConnectionExist()){
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        }
        else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("drop database " + databaseName);
                    System.out.println("База данных " + "\"" + databaseName + "\" " + "создана.");
                } catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }
            }
        }
    }
    //------------------------------------------------------------------------------------------------

    //Создание таблицы администраторов

    public void createTableAdministrators() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists administrators(" +
                            "id serial primary key, " +
                            "login character varying (30)," +
                            "password character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Администраторы\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------Создание таблиц------------------------------------------------------

    //Создание таблицы факультетов

    public void createTableFaculties() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists faculties(" +
                            "id serial primary key, " +
                            "name character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Факультеты\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------
    //Создание таблицы кафедр

    public void createTableDepartments() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists departments(" +
                            "id serial primary key, " +
                            "faculties_id integer references faculties(id)," +
                            "name character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Кафедры\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------

    public void createTableTeachers() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists teachers(" +
                            "id serial primary key, " +
                            "name character varying (30)," +
                            "surname character varying (30)," +
                            "patronymic character varying (30)," +
                            "login character varying(30),"+
                            "password character varying(30)"+
                            ");");
                    System.out.println("Таблица " + "\"Преподаватели\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }

    // Таблица преподаватели + кафедры

    public void createTableTeachersPlusDepartments() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists teachers_plus_departments(" +
                            "id serial primary key, " +
                            "teachers_id integer references departments(id)" +
                            ");");
                    System.out.println("Таблица " + "\"Преподаватели + Кафедры\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }


    //------------------------------------------------------------------------------------------------

    // Таблица групп

    public void createTableGroups() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists groups(" +
                            "id serial primary key, " +
                            "name character varying (30)," +
                            "form character varying (30)," +
                            "year character varying (30)," +
                            "login character varying(30)"+
                            ");");
                    System.out.println("Таблица " + "\"Группы\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }

    //Таблица группы + кафедры

    public void createTableGroupsPlusDepartments() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists groups_plus_departments(" +
                            "id serial primary key, " +
                            "groups_id integer references departments(id)" +
                            ");");
                    System.out.println("Таблица " + "\"Группы плюс кафедры\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------

    //Таблица предметы

    public void createTableSubjects() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists subjects(" +
                            "id serial primary key, " +
                            "name character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Предметы\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }

    // Таблица предметы + кафедры

    public void createTableSubjectsPlusDepartments() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists subjects_plus_departments(" +
                            "id serial primary key, " +
                            "subjects_id integer references departments(id)" +
                            ");");
                    System.out.println("Таблица " + "\"Предметы плюс кафедры\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------

    public void createTableLoad() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists load(" +
                            "id serial primary key, " +
                            "name character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Виды нагрузки\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }

    //------------------------------------------------------------------------------------------------

    //Таблица тип плана

    public void createTablePlan() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists plan(" +
                            "id serial primary key, " +
                            "name character varying (30)" +
                            ");");
                    System.out.println("Таблица " + "\"Тип плана\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
    //------------------------------------------------------------------------------------------------

    // Таблица расписание звонко

    public void createTableRings() {

        if (!BaseConnector.getInstance().doesConnectionExist()) {
            System.out.println("Соеденение с сервером бд отсутствует\nСначала создайте соеденение.");
        } else {
            if (statement == null) {
                try {
                    statement = BaseConnector.getInstance().getConnection().createStatement();
                    statement.executeUpdate("create table if not exists rings(" +
                            "id serial primary key, " +
                            "number character varying (30)," +
                            "start character varying(30),"+
                            "end character varying(30)"+
                            ");");
                    System.out.println("Таблица " + "\"Расписание звонков\" " + "создана.");
                }catch (SQLException e) {
                    System.out.println("Не удалось создать запрос: ");
                    e.printStackTrace();
                }

            }
        }
    }
}

package com.rudenko.models;

public class QueriesData {


    private QueriesData(){}

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------Создание таблиц------------------------------------------------------

    public static final String CREATE_ADMINISTRATORS = "create table if not exists administrators(" +
            "id serial primary key, " +
            "login character varying (30)," +
            "password character varying (30)" +
            ")";

    public static final String CREATE_TEACHERS = "create table if not exists teachers(" +
            "id serial primary key, " +
            "name character varying (30)," +
            "surname character varying (30)," +
            "patronymic character varying (30)," +
            "login character varying(30)," +
            "password character varying(30)" +
            ")";

    public static final String CREATE_FACULTIES = "create table if not exists faculties(" +
            "id serial primary key, " +
            "name character varying (30)" +
            ")";

    public static final String CREATE_DEPARTMENTS = "create table if not exists departments(" +
            "id serial primary key, " +
            "faculties_id integer references faculties(id)," +
            "name character varying (30)" +
            ")";

    public static final String CREATE_TEACHERS_PLUS_DEPARTMENTS = "create table if not exists teachers_plus_departments(" +
            "id serial primary key, " +
            "teachers_id integer references departments(id)" +
            ")";

    public static final String CREATE_GROUPS = "create table if not exists groups(" +
            "id serial primary key, " +
            "name character varying (30)," +
            "form character varying (30)," +
            "year character varying (30)" +
            ")";

    public static final String CREATE_GROUPS_PLUS_DEPARTMENTS = "create table if not exists groups_plus_departments(" +
            "id serial primary key, " +
            "groups_id integer references departments(id)" +
            ")";

    public static final String CREATE_SUBJECTS = "create table if not exists subjects(" +
            "id serial primary key, " +
            "name character varying (30)" +
            ")";

    public static final String CREATE_SUBJECTS_PLUS_DEPARTMENTS = "create table if not exists subjects_plus_departments(" +
            "id serial primary key, " +
            "subjects_id integer references departments(id)" +
            ")";


    public static final String CREATE_LOAD = "create table if not exists load(" +
            "id serial primary key, " +
            "name character varying (30)" +
            ")";

    public static final String CREATE_PLAN = "create table if not exists plan(" +
            "id serial primary key, " +
            "name character varying (30)" +
            ")";

    public static final String CREATE_RINGS = "create table if not exists rings(" +
            "id serial primary key, " +
            "number character varying (30)," +
            "begin character varying(30)," +
            "finish character varying(30)" +
            ")";
}

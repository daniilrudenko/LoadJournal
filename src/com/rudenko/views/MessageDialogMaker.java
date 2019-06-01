package com.rudenko.views;

import javafx.scene.control.Alert;

public class MessageDialogMaker {

    private Alert alertObj;   // Ссылка на объект класса "Alert"
    //-------------------------------------------

    //Конструктор с 4 переменными - заголовок, сообщение, содержание и тип окна (информационное, предупреждение и тд)

    public MessageDialogMaker(String title, String headerText, String contentText, Alert.AlertType alertType){

        alertObj = new Alert(alertType);
        if (alertType == null) alertObj.setAlertType(Alert.AlertType.INFORMATION); // Значение по умолчанию
        alertObj.setTitle(title);
        alertObj.setHeaderText(headerText);
        alertObj.setContentText(contentText);
    }
    //-------------------------------------------

    //Конструктор без параметров

    public MessageDialogMaker() throws NullPointerException{
        alertObj = new Alert(Alert.AlertType.INFORMATION);
    } // Значение по умолчанию
    //-------------------------------------------

    //Методная установка значений

    public void setTitle(String obj){
        alertObj.setTitle(obj);
    }

    public void setHeader(String obj){
        alertObj.setHeaderText(obj);
    }

    public void setContent(String obj){
        alertObj.setContentText(obj);
    }

    public void setAlertObj(Alert.AlertType alertType){
        alertObj.setAlertType(alertType);
    }
    //-------------------------------------------

    //Отображение окна и ожидание действий от пользователя
    public void show(){

        alertObj.showAndWait();
    }

}

package com.rudenko.models;

;
import com.rudenko.views.LimitedTextField;
import javafx.scene.control.TextField;

public class ControlOpportunitiesImprover {         // Класс служит помошником для работы с JavaFX Controls

   static final public String TRUE   = "TRUE";
   static final public String FALSE  = "FALSE";
   //--------------------------------------------

   // Данный метод получает массив тестовых полей, и проверяет, пустые они или нет
    public static String textFieldIsEmpty(TextField...textFields){

        String isEmpty = null;

        for(TextField textField : textFields){
            if(textField.getText().equals("")) {
                isEmpty = TRUE;
                break;
            }
            else isEmpty = FALSE;
        }
        return isEmpty;
    }
}

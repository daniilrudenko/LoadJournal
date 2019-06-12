package com.rudenko.models;

;
import com.rudenko.views.LimitedTextField;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.transform.Source;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControlsOpportunitiesImprover {         // Класс служит помошником для работы с JavaFX Controls

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

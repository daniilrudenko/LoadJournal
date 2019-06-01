package com.rudenko.views;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;



public class LimitedTextField extends TextField {

    private  IntegerProperty maxLength; // Значение максимальной длины
    //----------------------------------------------------------------------

    public LimitedTextField() {
        super();
        this.maxLength = new SimpleIntegerProperty(-1); // Установка значения -1 по умолчанию (Неограниченное количество символов)
    }
    //----------------------------------------------------------------------

    public void setMaxLength(int i){
        maxLength.set(i);
    } // Установка ограничения на количество символов

    //----------------------------------------------------------------------

    public IntegerProperty maxLengthProperty() {
        return this.maxLength;
    }

    //----------------------------------------------------------------------

    public final Integer getMaxLength() {
        return this.maxLength.getValue();
    }

    //----------------------------------------------------------------------

    @Override
    public void replaceText(int start, int end, String insertedText) {
        if (this.getMaxLength() <= 0) {

            super.replaceText(start, end, insertedText);
        }
        else {

            String currentText = this.getText() == null ? "" : this.getText();


            String finalText = currentText.substring(0, start) + insertedText + currentText.substring(end);


            int numberOfExceedingCharacters = finalText.length() - this.getMaxLength();

            if (numberOfExceedingCharacters <= 0) {

                super.replaceText(start, end, insertedText);
            }
            else {

                String cutInsertedText = insertedText.substring(
                        0,
                        insertedText.length() - numberOfExceedingCharacters
                );


                super.replaceText(start, end, cutInsertedText);
            }
        }
    }
    //----------------------------------------------------------------------
}

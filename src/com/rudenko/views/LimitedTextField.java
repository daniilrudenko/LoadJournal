package com.rudenko.views;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class LimitedTextField extends TextField  {

    /*
        Внимание!
        Для нужд разработки были реализованы вариации полей ввода (TextField)
        Добавлены возможности ввода без пробелов, и возможность установки ограничения на количество символов
        Однако, метод суперкласса setText обработан не был, по причинам остутствия необходимости.
        Поэтому, в случае расширения данной программы, следует на это обратить внимание

     */

    private IntegerProperty maxLength; // Значение максимальной длины
    //----------------------------------------------------------------------

    public LimitedTextField() {
        super();
        this.maxLength = new SimpleIntegerProperty(-1);
        // Установка значения -1 по умолчанию (Неограниченное количество символов)
    }
    //----------------------------------------------------------------------
    // Установка ограничения на количество символов

    public void setMaxLength(int i) {
        maxLength.set(i);
    }
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
        } else {

            String currentText = this.getText() == null ? "" : this.getText();
            String finalText = currentText.substring(0, start) + insertedText + currentText.substring(end);
            int numberOfExceedingCharacters = finalText.length() - this.getMaxLength();

            if (numberOfExceedingCharacters <= 0) {
                super.replaceText(start, end, insertedText);
            } else {
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

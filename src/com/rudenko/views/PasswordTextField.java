package com.rudenko.views;



public class PasswordTextField extends SpacesBannedTextField {

    /*
        Внимание!
        Для нужд разработки были реализованы вариации полей ввода (TextField)
        Добавлены возможности ввода без пробелов, и возможность установки ограничения на количество символов
        Однако, метод суперкласса setText обработан не был, по причинам остутствия необходимости.
        Поэтому, в случае расширения данной программы, следует на это обратить внимание

     */

    public void setValueOfTextField(String valueOfTextField) {
        this.valueOfTextField = valueOfTextField;
    }

    private String valueOfTextField = "";
    private SpacesBannedTextField textField;
    //-------------------------------------------------------

    public String getValueOfTextField() {
        return valueOfTextField;
    }
    //-------------------------------------------------------

    @Override
    public void replaceText(int start, int end, String insertedText) {
        if(insertedText.equals(" ")){
            super.replaceText(start,end,insertedText);
            return;
        }
        if(insertedText.equals(".")){
            insertedText = "";
            super.replaceText(start,end,insertedText);
            return;
        }

        textField.replaceText(start,end,insertedText);
        valueOfTextField = textField.getText();
        insertedText = insertedText.replaceAll("(?s).", "*");
        super.replaceText(start,end,insertedText);
    }


    //-------------------------------------------------------

    public PasswordTextField(){
        super();
        textField = new SpacesBannedTextField();
    }
}

package com.rudenko.views;



public class SpacesBannedTextField extends LimitedTextField {

    /*
        Внимание!
        Для нужд разработки были реализованы вариации полей ввода (TextField)
        Добавлены возможности ввода без пробелов, и возможность установки ограничения на количество символов
        Однако, метод суперкласса setText обработан не был, по причинам остутствия необходимости.
        Поэтому, в случае расширения данной программы, следует на это обратить внимание

     */



    @Override
    public void replaceText(int start, int end, String insertedText) {
        insertedText = insertedText.replace(" ", "");

        super.replaceText(start,end,insertedText);
    }

    public SpacesBannedTextField(){
       super();

    }
}

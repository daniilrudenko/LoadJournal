package com.rudenko.views;

public class SpacesBannedTextField extends LimitedTextField {


    @Override
    public void replaceText(int start, int end, String insertedText) {
        insertedText = insertedText.replace(" ", "");
        super.replaceText(start,end,insertedText);
    }
}

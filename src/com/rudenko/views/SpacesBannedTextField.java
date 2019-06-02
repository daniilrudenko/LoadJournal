package com.rudenko.views;

import javafx.scene.control.TextFormatter;

public class SpacesBannedTextField extends LimitedTextField {

    // TextField без пробелов с возможностью ограничить количество символов

    private TextFormatter<?> formatter = null;

    public SpacesBannedTextField(){
        super();
        formatter = new TextFormatter<>((TextFormatter.Change change) -> {
            String text = change.getText();

            if (!text.isEmpty()) {
                String newText = text.replace(" ", "").toLowerCase();

                int carretPos = change.getCaretPosition() - text.length() + newText.length();
                change.setText(newText);

                change.selectRange(carretPos, carretPos);
            }
            return change;
        });
        this.setTextFormatter(formatter);
    }
}

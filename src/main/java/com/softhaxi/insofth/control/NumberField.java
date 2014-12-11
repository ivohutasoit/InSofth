package com.softhaxi.insofth.control;

import javafx.scene.control.TextField;

/**
 *
 * @author Hutasoit
 */
public class NumberField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        String oldValue = getText();
        if (!text.matches("[A-Za-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            super.replaceText(start, end, text);
        }
        if (getText().length() > 2 ) {
            setText(oldValue);
        }
    }

    @Override
    public void replaceSelection(String text) {
        String oldValue = getText();
        if (!text.matches("[A-Za-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            super.replaceSelection(text);
        }
        if (getText().length() > 2 ) {
            setText(oldValue);
        }
    }
}

package com.softhaxi.insofth.utils;

import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Hutasoit
 */
public class ValidationUtil {
    /** 
     * Numeric Validation Limit the  characters to maximum length and only digits 
     * @param length maximum length of number
     * @return 
     */
    public static EventHandler<KeyEvent> NumberValidation(final Integer length) {
        return (KeyEvent e) -> {
            TextInputControl input = (TextInputControl) e.getSource();
            if (input.getText().length() >= length) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[0-9.]")){ 
                if(input.getText().contains(".") && e.getCharacter().matches("[.]")){
                    e.consume();
                }else if(input.getText().length() == 0 && e.getCharacter().matches("[.]")){
                    e.consume(); 
                }
            }else{
                e.consume();
            }
        };
    }
    
    /**
     * Letters Validation Limit the  characters to maxLengh AND to ONLY Letters
     * @param length maximum length of letter
     * @return 
     */
    public EventHandler<KeyEvent> TextValidation(final Integer length) {
    return (KeyEvent e) -> {
        TextInputControl input = (TextInputControl) e.getSource();
        if (input.getText().length() >= length) {
            e.consume();
        }
        if(e.getCharacter().matches("[A-Za-z]")){
        }else{
            e.consume();
        }
    };
}    
    
}

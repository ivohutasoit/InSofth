/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softhaxi.insofth.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author Hutasoit
 */
public class DateUtil {
    /**
     * 
     * @param pattern
     * @return 
     */
    public static StringConverter getDateFormat(String pattern) {
        return new StringConverter<LocalDate>() {
            DateTimeFormatter formater = 
                DateTimeFormatter.ofPattern(pattern);
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formater.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formater);
                } else {
                    return null;
                }
            }
            
        };
    }
    
    /**
     * 
     * @param before
     * @return 
     */
    public static Callback<DatePicker, DateCell> getDateFactory(LocalDate before) {
        return new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(DatePicker param) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item.isBefore(before.plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb");
                        }
                    }
                };
            }
        };
    }
    
    /**
     * 
     * @param date
     * @return 
     */
    public static int getDateInt(LocalDate date) {
        return (date.getYear() * 10000) + (date.getMonthValue()*100) + date.getDayOfMonth();
    }
}

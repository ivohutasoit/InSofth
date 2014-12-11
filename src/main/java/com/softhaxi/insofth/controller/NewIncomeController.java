package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.dao.FinanceAccount;
import com.softhaxi.insofth.enums.FinanceActivity;
import com.softhaxi.insofth.utils.ConfigurationUtil;
import com.softhaxi.insofth.utils.DateUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class NewIncomeController implements Initializable {
    private FinanceActivity type;
    private FinanceAccount account;
    
    @FXML private DatePicker dtpDate;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtpDate.setValue(LocalDate.now());
        dtpDate.setConverter(DateUtil.getDateFormat(ConfigurationUtil.App.getDateFormat()));
    }    

    /**
     * @return the type
     */
    public FinanceActivity getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FinanceActivity type) {
        this.type = type;
    }
   
    
}

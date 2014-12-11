package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.utils.ConfigurationUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class FooterController implements Initializable {
    private ResourceBundle bundle;
    @FXML private Label lblStatus;
    @FXML private ProgressBar prgProgress;
    @FXML private Label lblCompany;
    @FXML private Label lblCopyright;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        prgProgress.setVisible(false);
        setStatus(bundle.getString("ready.title"));
        lblCompany.setText(ConfigurationUtil.App.getCompany());
        lblCopyright.setText(ConfigurationUtil.App.getCopyright());
    }    
    
    /**
     * 
     * @param show 
     */
    public void showingProgressbar(boolean show) {
        prgProgress.setVisible(show);
    }
    
    /**
     * 
     * @param message 
     */
    public void setStatus(String message) {
        lblStatus.setText(message);
    }
}

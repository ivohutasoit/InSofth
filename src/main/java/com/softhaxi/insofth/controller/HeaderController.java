package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.utils.RSAUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class HeaderController implements Initializable {
    private DropShadow dropShadow;
    
    @FXML
    private HBox headerPane;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropShadow = new DropShadow();
        dropShadow.setRadius(4.0);
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.color(1.0, 0.4, 0.0));
        
        headerPane.setEffect(dropShadow);
    }    
    
    @FXML
    public void handleSettingClick(ActionEvent e) throws Exception{
        Stage setting = new Stage();
        setting.setTitle("Setting");
        setting.initModality(Modality.WINDOW_MODAL);
        setting.initStyle(StageStyle.UTILITY);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SettingScene.fxml"));
        Parent page = (Parent) fxmlLoader.load();          
        Scene scene = new Scene(page);
        
        setting.setScene(scene);
        setting.setResizable(false);
        setting.showAndWait();
    }
    
    @FXML
    private void handleActivation(ActionEvent e) {
        try {
            RSAUtil.generateKey();
        } catch (IOException ex) {
            Logger.getLogger(HeaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

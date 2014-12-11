/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.enums.FinanceActivity;
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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class MainController implements Initializable {

    private ResourceBundle bundle;

    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;

    @FXML
    private Button btnNewIncome;
    @FXML
    private Button btnNewExpense;
    @FXML
    private Button btnNewTransfer;
    @FXML
    private Button btnNewBudget;
    @FXML
    private Button btnNewRecurring;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
    }

    @FXML
    private void handleNewActivity(ActionEvent e) {
        Button btn = (Button) e.getSource();
        Parent root = null;
        NewIncomeController ctrl = null;
        try {
            if (btn == btnNewIncome) {

                loader = new FXMLLoader(getClass().getResource("/fxml/NewIncomeScene.fxml"), bundle);
                root = (Parent) loader.load();
                ctrl = loader.getController();
                scene = new Scene(root);
                stage = new Stage();
                stage.setTitle(bundle.getString("new.income"));
                stage.getIcons().add(new Image("/images/in_logo.png"));
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnNewIncome.getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();
                stage.setOnCloseRequest((WindowEvent event) -> {
                    stage = null;
                    scene = null;
                    loader = null;
                });
                
                return;

            }

            if (btn == btnNewExpense) {
                Dialogs.create()
                        .owner(btnNewExpense.getScene().getWindow())
                        .style(DialogStyle.NATIVE)
                        .lightweight()
                        .title(bundle.getString("app.name"))
                        .message(FinanceActivity.EXPENSE.toString())
                        .showInformation();
                return;
            }

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

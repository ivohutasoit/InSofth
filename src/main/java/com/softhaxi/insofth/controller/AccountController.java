package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.dao.FinanceAccount;
import com.softhaxi.insofth.dao.FinanceAccountGroup;
import com.softhaxi.insofth.utils.HibernateUtil;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class AccountController implements Initializable {
    private ResourceBundle bundle;
    private Session session;
    private FinanceAccount acc;
    
    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;
    

    @FXML
    private RadioButton rdoPersonal;
    @FXML
    private RadioButton rdoOrg;
    @FXML
    private RadioButton rdoCompany;
    @FXML
    private TextField txtAccount;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        buildData();
        handleGroupChanged();
    }

    private void buildData() {
        buildGroups();
    }

    /**
     *
     */
    private void buildGroups() {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "from FinanceAccountGroup";
        List<FinanceAccountGroup> groups = session.createQuery(sql).list();
        for (FinanceAccountGroup group : groups) {
            switch (group.getId()) {
                case "000001":
                    if (group.getStatus() == 1) {
                        rdoPersonal.setDisable(false);
                    } else {
                        rdoPersonal.setDisable(true);
                    }
                    if (group.getMain() == 1) {
                        rdoPersonal.setSelected(true);
                    } else {
                        rdoPersonal.setSelected(false);
                    }
                    rdoPersonal.setUserData(group);
                    break;
                case "000002":
                    if (group.getStatus() == 1) {
                        rdoOrg.setDisable(false);
                    } else {
                        rdoOrg.setDisable(true);
                    }
                    if (group.getMain() == 1) {
                        rdoOrg.setSelected(true);
                    } else {
                        rdoOrg.setSelected(false);
                    }
                    rdoOrg.setUserData(group);
                    break;
                case "000003":
                    if (group.getStatus() == 1) {
                        rdoCompany.setDisable(false);
                    } else {
                        rdoCompany.setDisable(true);
                    }
                    if (group.getMain() == 1) {
                        rdoCompany.setSelected(true);
                    } else {
                        rdoCompany.setSelected(false);
                    }
                    rdoCompany.setUserData(group);
                    break;
            }
        }
        session.close();
    }

    @FXML
    private void handleGroupChanged() {
        session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.getNamedQuery("FinanceAccount.FindDefaultByGroup");
        if (rdoPersonal.isSelected())  
            query.setString("group", ((FinanceAccountGroup) rdoPersonal.getUserData()).getId());
        else if (rdoOrg.isSelected())
            query.setString("group", ((FinanceAccountGroup) rdoOrg.getUserData()).getId());
        else
            query.setString("group", ((FinanceAccountGroup) rdoCompany.getUserData()).getId());
        
        List<FinanceAccount> accounts = query.list();
        for (FinanceAccount account : accounts) {
            if (account.getMain() == 1) {
                txtAccount.setText(String.format("%s (%s)", account.getName(), account.getCurrency()));
                txtAccount.setUserData(account);
                break;
            }
        }
        accounts = null;
        session.close();
    }
    
    @FXML
    private void handleSelect() {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/ListAccountScene.fxml"),bundle);
            Parent root = (Parent) loader.load();
            ListAccountController ctrl = loader.getController();
            if (rdoPersonal.isSelected()) {
                ctrl.setGroup((FinanceAccountGroup) rdoPersonal.getUserData());
            } else if (rdoOrg.isSelected()) {
                ctrl.setGroup((FinanceAccountGroup) rdoOrg.getUserData());
            } else {
                ctrl.setGroup((FinanceAccountGroup) rdoCompany.getUserData());
            }
            scene = new Scene(root, 600, 400);
            stage = new Stage();
            stage.setTitle(bundle.getString("select.account"));
            stage.getIcons().add(new Image("/images/in_logo.png"));
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(txtAccount.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
            stage.setOnCloseRequest((WindowEvent event) -> {
                acc = (FinanceAccount) root.getUserData();
                txtAccount.setUserData(acc);
                txtAccount.setText(String.format("%s (%s)", acc.getName(), acc.getCurrency()));
                handleAccountChanged();
            });
        } catch (IOException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAccountChanged() {
        
    }

}

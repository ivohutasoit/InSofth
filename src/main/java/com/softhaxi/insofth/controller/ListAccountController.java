package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.dao.FinanceAccount;
import com.softhaxi.insofth.dao.FinanceAccountGroup;
import com.softhaxi.insofth.utils.HibernateUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class ListAccountController implements Initializable {
    private ResourceBundle bundle;
    private Session session;
    
    private FinanceAccountGroup group;
    private ObservableList<FinanceAccount> accounts;

    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;
    
    @FXML
    private TableView<FinanceAccount> tblAccount;
    @FXML
    private TableColumn<FinanceAccount, String> tcId;
    @FXML
    private TableColumn<FinanceAccount, String> tcName;
    @FXML
    private TableColumn<FinanceAccount, String> tcDescription;

    /**
     * @return the group
     */
    public FinanceAccountGroup getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(FinanceAccountGroup group) {
        this.group = group;
        loadData();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcName.setCellValueFactory(new PropertyValueFactory("name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("remark"));
    }

    private void loadData() {
        session = HibernateUtil.getSessionFactory().openSession();
        accounts = FXCollections.observableArrayList();
        accounts.addAll(session.getNamedQuery("FinanceAccount.FindByGroup").setString("group", group.getId()).list());
        tblAccount.setItems(accounts);

        session.close();
    }
    
    @FXML
    private void handleNewAccount() {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/NewAccountScene.fxml"), bundle);
            Parent root = (Parent) loader.load();
            NewAccountController ctrl = loader.getController();
            ctrl.setGroup(group);
            scene = new Scene(root);
            stage = new Stage();
            stage.setTitle(bundle.getString("new.account"));
            stage.getIcons().add(new Image("/images/in_logo.png"));
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblAccount.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
            stage.setOnCloseRequest((WindowEvent event) -> {
                loadData();
            });
        } catch (IOException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

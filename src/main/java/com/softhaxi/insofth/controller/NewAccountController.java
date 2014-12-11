package com.softhaxi.insofth.controller;

import com.softhaxi.insofth.dao.ApplicationVariable;
import com.softhaxi.insofth.dao.FinanceAccount;
import com.softhaxi.insofth.dao.FinanceAccountGroup;
import com.softhaxi.insofth.dao.FinanceActivity;
import com.softhaxi.insofth.utils.DateUtil;
import com.softhaxi.insofth.utils.HibernateUtil;
import com.softhaxi.insofth.utils.ValidationUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hutasoit
 */
public class NewAccountController implements Initializable {
    private Session session;
    private FinanceAccountGroup group;

    @FXML private Parent root;
    @FXML private TextField txtAccType;
    @FXML private TextField txtName;
    @FXML private ComboBox<ApplicationVariable> cmbCurrency;
    @FXML private TextArea txtDescription;
    @FXML private TextField txtBalance;
    @FXML private CheckBox chkDefault;
    @FXML private Button btnSave;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        group = new FinanceAccountGroup();
        
        txtBalance.addEventFilter(KeyEvent.KEY_TYPED, ValidationUtil.NumberValidation(Integer.SIZE));

        ValidationSupport validator = new ValidationSupport();
        validator.registerValidator(txtName, Validator.createEmptyValidator("Account name is required"));
        validator.registerValidator(txtBalance, Validator.createEmptyValidator("Initial Balance is required"));

        validator.validationResultProperty().addListener((o, oldValue, newValue) -> {
            if(newValue.getErrors().isEmpty())
                btnSave.setDisable(false);
            else
                btnSave.setDisable(true);
        });
        
        session = HibernateUtil.getSessionFactory().openSession();
        final ObservableList<ApplicationVariable> currencies = FXCollections.observableArrayList();
        currencies.addAll(session.createQuery("from ApplicationVariable where type='CRY'").list());
        session.close();
        cmbCurrency.setItems(currencies);
        cmbCurrency.getSelectionModel().selectFirst();
        cmbCurrency.setCellFactory(new Callback<ListView<ApplicationVariable>, ListCell<ApplicationVariable>>() {

            @Override
            public ListCell<ApplicationVariable> call(ListView<ApplicationVariable> param) {
                final ListCell<ApplicationVariable> cell = new ListCell<ApplicationVariable>() {
                    @Override
                    protected void updateItem(ApplicationVariable m, boolean empty) {
                        super.updateItem(m, empty);
                        if(m != null) 
                            setText(String.format("%s %s", m.getValue(),m.getRemark()));
                         else
                            setText(null);
                    }
                };
                
                return cell;
            }
        });
        cmbCurrency.setConverter(new StringConverter<ApplicationVariable>() {

            @Override
            public String toString(ApplicationVariable m) {
                if(m == null)
                    return null;
                else
                    return String.format("%s %s", m.getValue(),m.getRemark());
            }

            @Override
            public ApplicationVariable fromString(String s) {
                String[] ars = s.split(" ");
                for (ApplicationVariable av : currencies) {
                    if(av.getValue().equals(ars[0]) && av.getRemark().equals(ars[1]))
                        return av;
                }
                return null;
            }
        });
    }

    /**
     *
     * @param group
     */
    public void setGroup(FinanceAccountGroup group) {
        this.group = group;
        txtAccType.setText(group.getName());
    }

    @FXML
    private void handleSave() {
        session = HibernateUtil.getSessionFactory().openSession();
        FinanceAccount acc = new FinanceAccount();
        FinanceActivity act = new FinanceActivity();
        
        int ii = group.getAccounts().size();
        acc.setGroup(group);
        acc.setId(String.format("%02d%04d", Integer.parseInt(group.getId()), ++ii));
        acc.setName(txtName.getText().trim());
        acc.setRemark(txtDescription.getText().trim());
        acc.setCurrency(cmbCurrency.getValue().getValue());
        acc.setBalance(Float.parseFloat(txtBalance.getText()));
        acc.setMain(chkDefault.isSelected() ? 1 : 0);
        
        act.setAccount(acc);
        act.setDate(DateUtil.getDateInt(LocalDate.now()));
        act.setAmount(acc.getBalance());
        act.setCategory("I001");
        act.setSubCategory("I101");
        act.setPayMethod("P01");
        act.setRemark("Initial Balance");
        
        session.beginTransaction();
        session.saveOrUpdate(acc);
        session.saveOrUpdate(act);
        
        
        if(acc.getMain() == 1) {
            for (Iterator it = group.getAccounts().iterator(); it.hasNext();) {
                FinanceAccount ac = (FinanceAccount) it.next();
                
                if(ac.getMain() == 1) {
                    ac.setMain(0);
                    session.saveOrUpdate(ac);
                    break;
                }
            }
        }
        
        session.flush();
        session.getTransaction().commit();
        session.close();
        ((Stage) txtAccType.getScene().getWindow()).close();
    }

    @FXML
    private void handleClose() {
        group = null;
        ((Stage) txtAccType.getScene().getWindow()).close();
    }
}

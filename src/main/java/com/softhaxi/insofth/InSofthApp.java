package com.softhaxi.insofth;

import com.softhaxi.insofth.utils.ConfigurationUtil;
import static com.softhaxi.insofth.utils.ConfigurationUtil.CONFIG_PATH;
import static com.softhaxi.insofth.utils.ConfigurationUtil.DBCONF_PATH;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Hutasoit
 */
public class InSofthApp extends Application {
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
        launch(args);
    }
    
    /**
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = new PropertyResourceBundle(getClass().getResource("/bundles/insofth.properties").openStream());
        try {
            FileInputStream in0=new FileInputStream(CONFIG_PATH);
            FileInputStream in1=new FileInputStream(DBCONF_PATH);
        } catch (FileNotFoundException ex) {
            Dialogs.create()
                    .title(bundle.getString("app.name"))
                    .showException(ex);
            
            return;
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"), bundle);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle(ConfigurationUtil.App.getName() + " " + ConfigurationUtil.App.getVersion());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/in_logo.png"));
        stage.setMaximized(true);

        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }
    
}

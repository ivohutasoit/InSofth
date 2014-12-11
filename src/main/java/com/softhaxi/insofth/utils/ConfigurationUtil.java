package com.softhaxi.insofth.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hutasoit
 */
public class ConfigurationUtil {

    public static final String CONFIG_PATH = "@./../config.properties";
    public static final String DBCONF_PATH = "@./../dbconf.properties";

    private static final Properties config = new Properties();
    private static final Properties dbconf = new Properties();

    static {
        try {
            FileInputStream in0 = new FileInputStream(CONFIG_PATH);
            config.load(in0);
            FileInputStream in1 = new FileInputStream(DBCONF_PATH);
            dbconf.load(in1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigurationUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    public static class App {

        /**
         *
         * @return
         */
        public static String getName() {
            return config.getProperty("app.name");
        }

        /**
         *
         * @return
         */
        public static String getVersion() {
            return config.getProperty("app.version");
        }

        /**
         *
         * @return
         */
        public static String getCompany() {
            return config.getProperty("app.company");
        }

        /**
         *
         * @return
         */
        public static String getCopyright() {
            return config.getProperty("app.copyright");
        }

        /**
         *
         * @return
         */
        public static String getDateFormat() {
            return config.getProperty("date.format");
        }

        /**
         *
         * @return
         */
        public static String getDateWeek() {
            return config.getProperty("date.week");
        }
    }
}

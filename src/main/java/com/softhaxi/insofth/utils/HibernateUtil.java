package com.softhaxi.insofth.utils;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Hutasoit
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            // sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            AnnotationConfiguration conf = new AnnotationConfiguration()
                    .addPackage("com.softhaxi.insofth.dao")
                    .addResource("com/softhaxi/insofth/dao/ApplicationVariable.hbm.xml")
                    .addResource("com/softhaxi/insofth/dao/FinanceAccountGroup.hbm.xml")
                    .addResource("com/softhaxi/insofth/dao/FinanceAccount.hbm.xml")
                    .addResource("com/softhaxi/insofth/dao/FinanceActivity.hbm.xml")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/isftdb?zeroDateTimeBehavior=convertToNull")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "pratesis")
                    .setProperty("hibernate.show_sql", "true");
            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

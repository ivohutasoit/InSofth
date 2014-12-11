package com.softhaxi.insofth.model;

import com.softhaxi.insofth.utils.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author Hutasoit
 */
public class Context {
    private static Context instance = null;
    
    private Session session;
    
    private Context() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return 
     */
    public static Context getInstance() {
        if(instance == null)
            instance = new Context();
        
        return instance;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
    
    
}

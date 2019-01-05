package tk.xdevcloud.medicalcore.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
/**
 * Application Lifecycle Listener implementation class DBManagerListener
 *
 */
import javax.persistence.Persistence;
@WebListener
public class DBManagerListener implements ServletContextListener, ServletRequestListener {

    /**
     * Default constructor. 
     */
	private static EntityManagerFactory  emFactory = null; 
    public DBManagerListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    	if(emFactory != null) {
    		
    		emFactory.close();
    	}
        
    }
    /**
     * Entity Manager to be used in servlets
     * @return EntityManager if exists
     * @throws Exception
     */
    public static EntityManager getEntityManager() throws Exception {
   
    	if(emFactory != null) {
    		
    		return emFactory.createEntityManager();
    	}
    	else {
    		 throw new Exception("Database not initialized");
    	}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	
    	emFactory = Persistence.createEntityManagerFactory("medicaldb");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author carlos
 */
public class HibernateUtil {

  private static final SessionFactory sessionFactory;
  
  static {
    try {
        // Para Hibernate 4.3.x 
        // Crear  SessionFactory desde hibernate.cfg.xml 
//        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");     
//        return configuration.buildSessionFactory( new StandardServiceRegistryBuilder().applySettings( configuration.getProperties() ).build() );

        // Para Hibernate 5.x 
        // Crear SessionFactory desde hibernate.cfg.xml
 	//StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
      //  Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        //sessionFactory= metadata.getSessionFactoryBuilder().build();
    	
    	//Otra manera para v5.x
      // Create the SessionFactory from standard (hibernate.cfg.xml) 
      // config file.
//      sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//    	sessionFactory = new Configuration().configure(otraConfiguracion.cfg.xml).buildSessionFactory();
    	//toda la info en: https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#_system_requirements
     sessionFactory = new Configuration().configure().buildSessionFactory();
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

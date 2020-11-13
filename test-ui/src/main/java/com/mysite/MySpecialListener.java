package com.mysite;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySpecialListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent contextEvent) {

    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
        try {
            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:postgresql://localhost:5432/NIR");
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}


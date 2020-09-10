package com.parkit.parkingsystem.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseConfig {
	private static final String  dbConfigPath="./application.properties";
    private static final Logger logger = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
    	
        logger.info("Create DB connection");
        logger.info(dataBaseProperties().getProperty("db.url"));
        Connection c;
        Class.forName("com.mysql.cj.jdbc.Driver");
         c=DriverManager.getConnection(dataBaseProperties().getProperty("db.url"),dataBaseProperties().getProperty("db.user"),dataBaseProperties().getProperty("db.password"));
         return c;
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection",e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement",e);
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set",e);
            }
        }
    }
 
    private Properties dataBaseProperties() {
    		Properties prop = new Properties();
    		InputStream inputStream =getClass().getClassLoader().getResourceAsStream(dbConfigPath);
    		if(inputStream!=null) {
    			try {
					prop.load(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		return prop;
    	}

}

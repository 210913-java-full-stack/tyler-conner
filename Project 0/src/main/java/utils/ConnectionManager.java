package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection conn;

    private ConnectionManager() {

    }

    public static Connection getConnection() throws IOException, SQLException {
        if (conn == null) {
            Properties props = new Properties();
            FileReader connectionProperties = new FileReader("src/main/resources/connection.properties");
            props.load(connectionProperties);
            String connString = "jdbc:mariadb://" + props.getProperty("hostname") +  ":" + props.getProperty("port") + "/" + props.getProperty("databaseName") + "?user=" +
                    props.getProperty("user") + "&password=" + props.getProperty("password");
            conn = DriverManager.getConnection(connString);
        }
        return conn;
    }
}

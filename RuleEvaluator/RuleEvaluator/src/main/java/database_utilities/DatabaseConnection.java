package database_utilities;

import properties_reader.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connection;

    public Connection getConnection() throws SQLException {
        PropertiesReader propertiesReader= new PropertiesReader();
        String dbAddress = propertiesReader.readFromConfig("db_address", "rule_eval_config"),
                dbUser = propertiesReader.readFromConfig("db_user", "rule_eval_config"),
                dbPass = propertiesReader.readFromConfig("db_pass", "rule_eval_config");
        Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
        System.out.println("Connected To Database");
        return connection;
    }
}

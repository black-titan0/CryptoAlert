package database_utilities;

import alerts.Alert;
import org.slf4j.LoggerFactory;
import properties_reader.PropertiesReader;

import java.sql.*;

public class DatabaseConnection {
    Connection connection;

    public Connection getConnection() throws SQLException {
        PropertiesReader propertiesReader= new PropertiesReader();
        String dbAddress = propertiesReader.readFromConfig("db_address", "rule_eval_config"),
                dbUser = propertiesReader.readFromConfig("db_user", "rule_eval_config"),
                dbPass = propertiesReader.readFromConfig("db_pass", "rule_eval_config");
        connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
        LoggerFactory.getLogger(this.getClass()).info("Connected To Database Successfully");
        return connection;
    }

    public void insertAlert(Alert alert){
        try{
            PreparedStatement statement = connection.prepareStatement(" INSERT INTO alerts(time, name, market_name) VALUES (?, ?, ?);");
            statement.setString(2, alert.name);
            statement.setString(3, alert.marketName);
            statement.setTimestamp(1, Timestamp.valueOf(alert.time));
            statement.executeUpdate();
            LoggerFactory.getLogger(this.getClass()).info("Alert inserted to data base : " + alert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

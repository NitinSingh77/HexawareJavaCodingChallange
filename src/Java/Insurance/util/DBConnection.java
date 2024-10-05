package Java.Insurance.util;

import java.sql.*;

public class DBConnection {
	private static Connection connection;
    public static Connection getConnection(String propertyFileName) {
        if (connection == null) {
            try {
                String connectionString = PropertyUtil.getConnectionString(propertyFileName);
                connection = DriverManager.getConnection(connectionString);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
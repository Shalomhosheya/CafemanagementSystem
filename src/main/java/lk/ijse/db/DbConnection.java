package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cafe", "root", "shalom@12344321");
            return connect;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}

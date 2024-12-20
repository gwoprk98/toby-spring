package springbook.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnection implements ConnectionMaker {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3307/springbook", "spring", "book");
        } catch (SQLException e) {
            System.out.println("DB 연동 실패");
            throw new RuntimeException(e);
        }
    }
}

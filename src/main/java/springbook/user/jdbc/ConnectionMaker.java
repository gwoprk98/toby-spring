package springbook.user.jdbc;

import java.sql.Connection;

public interface ConnectionMaker {
    Connection getConnection();
}

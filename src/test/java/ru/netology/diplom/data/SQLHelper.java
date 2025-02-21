package ru.netology.diplom.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void clearTestData() {
        var connection = getConnect();
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static String requestsCount() {
        Long count = null;
        var codeSQL = "SELECT COUNT(*) FROM order_entity;";
        try (var connection = getConnect()) {
            count = QUERY_RUNNER.query(connection, codeSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count != null) {
            return Long.toString(count);
        }
        return "0";
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var codeSQL = "SELECT status FROM payment_entity";
        var connection = getConnect();
        return QUERY_RUNNER.query(connection, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var codeSQL = "SELECT status FROM credit_request_entity";
        var connection = getConnect();
        return QUERY_RUNNER.query(connection, codeSQL, new ScalarHandler<>());
    }

}



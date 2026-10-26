package hu.unideb.inf.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GameResultRepository {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public GameResultRepository(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        initializeTable();
    }

    private void initializeTable() {
        String createTableSql = """
                CREATE TABLE IF NOT EXISTS game_results (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    player_name VARCHAR(50) NOT NULL,
                    steps INT NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSql);

        } catch (SQLException e) {
            throw new RuntimeException("Hiba az adatbázis inicializálásakor: " + e.getMessage(), e);
        }
    }
}
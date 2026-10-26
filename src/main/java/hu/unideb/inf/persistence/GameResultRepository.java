package hu.unideb.inf.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public void saveResult(GameResult result) {
        String insertSql = "INSERT INTO game_results (player_name, steps) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            pstmt.setString(1, result.playerName());
            pstmt.setInt(2, result.steps());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Hiba az eredmény mentésekor: " + e.getMessage(), e);
        }
    }

    public List<GameResult> getTopResults(int limit) {
        String selectSql = "SELECT player_name, steps FROM game_results ORDER BY steps ASC LIMIT ?";
        List<GameResult> topResults = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            pstmt.setInt(1, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("player_name");
                    int steps = rs.getInt("steps");
                    topResults.add(new GameResult(name, steps));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Hiba az eredmények lekérdezésekor: " + e.getMessage(), e);
        }

        return topResults;
    }
}
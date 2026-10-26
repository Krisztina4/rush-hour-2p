package hu.unideb.inf.persistence;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameResultRepositoryTest {

    @Test
    void testSaveAndGetTopResults() {

        String dbUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        GameResultRepository repository = new GameResultRepository(dbUrl, "sa", "");

        repository.saveResult(new GameResult("Player1", 15));
        repository.saveResult(new GameResult("Player2", 10));
        repository.saveResult(new GameResult("Player3", 20));

        List<GameResult> topResults = repository.getTopResults(2);

        assertEquals(2, topResults.size());


        assertEquals("Player2", topResults.get(0).playerName());
        assertEquals(10, topResults.get(0).steps());


        assertEquals("Player1", topResults.get(1).playerName());
        assertEquals(15, topResults.get(1).steps());
    }
}
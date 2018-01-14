package service;

// servis do zapisywania rezultatów gry i pobierania informacji na temat ilości zagranych gier
import database.DAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameResultService {

    private DAO dao = DAO.getInstance();

    private static final Logger logger = LogManager.getLogger();

    public int fetchGamesPlayedCount() {
        return dao.getNumberOfGames();
    }

    public String showPlayerResults(String playerName) {
        int wins = dao.getUserWinSum(playerName);
        int loose = dao.getUserLooseSum(playerName);
        return playerName + " has won " + wins + " games and lost " + loose + " games";
    }

    public void saveResult(String winner, String looser) {
        dao.saveResult(winner, looser);
    }
}

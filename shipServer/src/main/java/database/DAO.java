package database;


import config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

// jak nazwa wskazuje - obiekt do komunikacji z bazą danych
public class DAO {

    private static final Logger logger = LogManager.getLogger();

    // data required for database connection
    private String url = "jdbc:mysql://" + Config.databaseHost() + ":" + Config.databasePort() + "/ships";
    private String user = Config.databaseLogin();
    private String password = Config.databasePassword();
    private static DAO dao = null;

    private DAO() {
    }

    // Singleton pattern for database connection
    public static DAO getInstance() {
        if(dao == null){
            dao = new DAO();
        }
        return dao;
    }

    public int getNumberOfGames(){
        try (Connection myCon = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement =
                myCon.prepareStatement("SELECT count(*) as amount from ships.results");
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if(resultSet.next()) {
                return resultSet.getInt("amount");
            }
        } catch (Exception e) {
            logger.error("Error while fetching games count!", e);
        }
        return 0;
    }

    public int getUserWinSum(String player){
        try (Connection myCon = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement =
                myCon.prepareStatement("SELECT sum(win) as sum from ships.results where player_name = '" + player + "'");
             ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if(resultSet.next()) {
                    return resultSet.getInt("sum");
                }

        } catch (SQLException e) {
            logger.error("Error while fetching sum of win games for user " + player, e);
        }
        return 0;
    }

    public int getUserLooseSum(String player){
        try(Connection myCon = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement =
                myCon.prepareStatement("SELECT sum(loose) as sum from ships.results where player_name = '" + player + "'");
            ResultSet resultSet = preparedStatement.executeQuery()
            ) {

            if(resultSet.next()) {
                return resultSet.getInt("sum");
            }

        } catch (SQLException e) {
            logger.error("Error while fetching sum of loose games for user " + player, e);
        }
        return 0;
    }

    public void saveResult(String winner, String looser) {

        try(Connection myCon = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatementWinner =
                    myCon.prepareStatement("insert into ships.results (player_name, win, end_game) values (?, 1, now())");
            PreparedStatement preparedStatementLooser =
                    myCon.prepareStatement("insert into ships.results (player_name, loose, end_game) values (?, 1, now())");
            ) {

            preparedStatementWinner.setString(1, winner);
            preparedStatementLooser.setString(1, looser);

            preparedStatementWinner.executeUpdate();
            preparedStatementLooser.executeUpdate();
            preparedStatementWinner.close();
            preparedStatementLooser.close();

        } catch (SQLException e) {
            logger.error("Error while saving results ", e);
        }
    }
}








package hu.nye;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the high scores for the Connect 4 game.
 */
public final class HighScore {
    /**
     * The database connection.
     */
    Connection connection;

    /**
     * The maximum number of high scores to display.
     */
    private static final int HIGHSCORE_LIST = 5;

    /**
     * Constructs a new HighScore instance and establishes a database connection.
     */
    public HighScore() {
        try {
            this.connection = newConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes a new database connection.
     *
     * @return the new database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection newConnection() throws SQLException {
        // loads driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Driver not found");
        }

        Connection conn;

        conn = DriverManager.getConnection("jdbc:mysql://localhost/"
                + "connect4?user=root");

        return conn;
    }

    /**
     * Creates the necessary tables for storing users and high scores if they do not exist.
     *
     * @throws SQLException if a database access error occurs
     */
    public void createTables() throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS "
                + "users (id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255))");

        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS "
                + "highscore (id INT NOT NULL AUTO_INCREMENT, "
                + "nameId INT,"
                + " score INT,"
                + " PRIMARY KEY (id),"
                + " FOREIGN KEY (nameId) REFERENCES users(id))");
    }

    /**
     * Inserts a new user into the database if they do not already exist.
     *
     * @param name the name of the user
     * @throws SQLException if a database access error occurs
     */
    public void insertUser(final String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("SELECT * FROM users WHERE name = '" + name + "'");
        ResultSet rs = stmt.getResultSet();
        // if user does not exist, insert it
        if (!rs.next()) {
            connection.createStatement().execute("INSERT INTO users (name) "
                    + "VALUES ('" + name + "')");
        }
    }

    /**
     * Increases the high score of the specified user by one.
     *
     * @param name the name of the user
     * @throws SQLException if a database access error occurs
     */
    public void increaseHighScore(final String name) throws SQLException {
        Statement stmt = connection.createStatement();
        // get the user's score
        stmt.execute("SELECT * FROM highscore "
                + "INNER JOIN users ON highscore.nameId = users.id "
                + "WHERE users.name = '" + name + "'");

        ResultSet rs = stmt.getResultSet();
        int score;
        if (rs.next()) {
            score = rs.getInt("score");
            score++;
            System.out.println("User previous score: " + score);

            stmt.execute("UPDATE highscore "
                    + "INNER JOIN users ON highscore.nameId = users.id "
                    + "SET score = " + score + " "
                    + "WHERE users.name = '" + name + "'");
        } else {
            stmt.execute("INSERT INTO highscore (nameId, score) "
                    + "SELECT id, 1 FROM users WHERE name = '" + name + "'");
        }
    }

    /**
     * Displays the top high scores.
     *
     * @throws SQLException if a database access error occurs
     */
    public void showHighScore() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("SELECT * FROM highscore "
                + "INNER JOIN users ON highscore.nameId = users.id "
                + "ORDER BY score DESC");
        ResultSet rs = stmt.getResultSet();
        int i = 0;
        while (rs.next()) {
            if (i == HIGHSCORE_LIST) {
                break;
            }
            System.out.println(rs.getString("name") + " "
                    + rs.getInt("score"));
            i++;
        }
    }
}

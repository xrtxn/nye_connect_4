package hu.nye;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public final class HighScore {
    
    private static final int HIGHSCORE_LIST = 5;
    
    private Connection connection;

    
    public HighScore() {
        try {
            this.connection = newConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
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

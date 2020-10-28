package me.jaskowicz.quantumpractice.UtilsExtra;

import me.jaskowicz.quantumpractice.Quantumpractice;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.HashMap;

public class DatabaseUtils {

    private Plugin plugin = Quantumpractice.getPlugin(Quantumpractice.class);

    public DatabaseUtils() {

    }

    public synchronized void openConnection() throws SQLException {
        if (Quantumpractice.connection != null && !Quantumpractice.connection.isClosed()) {
            return;
        }

        try {
            synchronized (this) {
                if (Quantumpractice.connection != null && !Quantumpractice.connection.isClosed()) {
                    return;
                }

                FileConfiguration configData = plugin.getConfig();

                Quantumpractice.connection = DriverManager.getConnection("jdbc:mysql://" + configData.getString("database.host") + "/" + configData.getString("database.database") + "?autoReconnect=true&tcpKeepAlive=true&useUnicode=yes&useSSL=false", "" + configData.getString("database.username"), "" + configData.getString("database.password"));
                //Statement cre = Quantumpractice.connection.createStatement();
                //cre.executeUpdate("CREATE TABLE IF NOT EXISTS `STATS`(`UserID` int NOT NULL AUTO_INCREMENT, `playerName` varchar (256), `playerUUID` varchar(256), `Kills` int, `Deaths` int, `Wins` int, `Loses` int, `Draws` int, PRIMARY KEY (`UserID`));");
                //cre.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void closeConnection() {
        try {
            Quantumpractice.connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean tableContainsPlayerUUID(String playerUUID, String table) {
        try {
            PreparedStatement sql = Quantumpractice.connection.prepareStatement("SELECT * FROM `" + table + "` WHERE playerUUID=?;");
            sql.setString(1, playerUUID);

            try {
                ResultSet resultset = sql.executeQuery();

                boolean containsGuild = resultset.next();

                sql.close();
                resultset.close();

                return containsGuild;
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static boolean tableContainsPlayerName(String playerName, String table) {
        try {
            PreparedStatement sql = Quantumpractice.connection.prepareStatement("SELECT * FROM `" + table + "` WHERE playerName=?;");
            sql.setString(1, playerName);

            try {
                ResultSet resultset = sql.executeQuery();

                boolean containsGuild = resultset.next();

                sql.close();
                resultset.close();

                return containsGuild;
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static HashMap<String, Integer> getTopFiveWins() {

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        try {
            Statement statement = Quantumpractice.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `STATS` ORDER BY `Wins` DESC LIMIT 5;");

            while (result.next()) {

                stringIntegerHashMap.put(result.getString("playerName"), result.getInt("Wins"));
            }

            result.close();
            statement.close();

            return stringIntegerHashMap;
        } catch(SQLException e) {
            e.printStackTrace();
            return stringIntegerHashMap;
        }
    }

    public static HashMap<String, Integer> getTopFiveLoses() {

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        try {
            Statement statement = Quantumpractice.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `STATS` ORDER BY `Loses` DESC LIMIT 5;");

            while (result.next()) {

                stringIntegerHashMap.put(result.getString("playerName"), result.getInt("Loses"));
            }

            result.close();
            statement.close();

            return stringIntegerHashMap;
        } catch(SQLException e) {
            e.printStackTrace();
            return stringIntegerHashMap;
        }
    }

    public static HashMap<String, Integer> getTopFiveDraws() {

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        try {
            Statement statement = Quantumpractice.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `STATS` ORDER BY `Draws` DESC LIMIT 5;");

            while (result.next()) {

                stringIntegerHashMap.put(result.getString("playerName"), result.getInt("Draws"));
            }

            result.close();
            statement.close();

            return stringIntegerHashMap;
        } catch(SQLException e) {
            e.printStackTrace();
            return stringIntegerHashMap;
        }
    }
}

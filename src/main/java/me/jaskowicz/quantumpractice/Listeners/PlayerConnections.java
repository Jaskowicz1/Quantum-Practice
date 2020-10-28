package me.jaskowicz.quantumpractice.Listeners;

import me.jaskowicz.quantumpractice.Quantumpractice;
import me.jaskowicz.quantumpractice.Utils.User;
import me.jaskowicz.quantumpractice.UtilsExtra.DatabaseUtils;
import me.jaskowicz.quantumpractice.UtilsExtra.ListenerExec;
import me.jaskowicz.quantumpractice.UtilsExtra.ScoreboardAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerConnections implements ListenerExec {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();

        User user = Quantumpractice.USERS.put(pl.getUniqueId(), new User(pl));

        if (DatabaseUtils.tableContainsPlayerUUID(pl.getUniqueId().toString(), "STATS")) {
            try {
                Statement statement = Quantumpractice.connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM `STATS` WHERE `playerUUID`='" + pl.getUniqueId().toString() + "';");

                if(result.next()) {
                    String kitName = result.getString("kitName");
                    user.kitWins.put(Quantumpractice.KITS.get(kitName), result.getInt("Wins"));
                    user.kitLoses.put(Quantumpractice.KITS.get(kitName), result.getInt("Loses"));
                    user.kitDraws.put(Quantumpractice.KITS.get(kitName), result.getInt("Draws"));
                }

                result.close();
                statement.close();

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        user.setCurrentScoreboardAPI(new ScoreboardAPI(pl));
    }

    @Override
    public boolean execute() {
        return true;
    }
}

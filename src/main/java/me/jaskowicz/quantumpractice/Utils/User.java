package me.jaskowicz.quantumpractice.Utils;

import me.jaskowicz.quantumpractice.UtilsExtra.ScoreboardAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class User {

    private final Player player;

    public HashMap<Kit, Integer> kitWins = new HashMap<>();
    public HashMap<Kit, Integer> kitLoses = new HashMap<>();
    public HashMap<Kit, Integer> kitDraws = new HashMap<>();

    private ScoreboardAPI currentScoreboardAPI;

    private boolean setupMode;

    private Game gameIn;

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGameIn() {
        return gameIn;
    }

    public ScoreboardAPI getCurrentScoreboardAPI() {
        return currentScoreboardAPI;
    }

    public boolean isSetupMode() {
        return setupMode;
    }


    // All voids after this line.


    public void setGameIn(Game gameIn) {
        this.gameIn = gameIn;
    }

    public void setCurrentScoreboardAPI(ScoreboardAPI currentScoreboardAPI) {
        this.currentScoreboardAPI = currentScoreboardAPI;
    }

    public void setSetupMode(boolean setupMode) {
        this.setupMode = setupMode;
    }
}

package me.jaskowicz.quantumpractice.UtilsExtra;

import me.jaskowicz.quantumpractice.Quantumpractice;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Objects;

public class ScoreboardAPI {

    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;

    private HashMap<String, String> teamEntries = new HashMap<>();

    private Plugin plugin = Quantumpractice.getPlugin(Quantumpractice.class);


    public ScoreboardAPI(Player player) {
        this.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        this.player = player;

        if(scoreboard.getObjective("QuantumPractice") != null) {
            scoreboard.getObjective("QuantumPractice").unregister();
            objective = scoreboard.registerNewObjective("QuantumPractice", "dummy", ChatColor.AQUA + "Quantum-Practice");
        } else {
            objective = scoreboard.registerNewObjective("QuantumPractice", "dummy", ChatColor.AQUA + "Quantum-Practice");
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public boolean teamExists(String teamName) {
        return scoreboard.getTeam(teamName) != null;
    }


    // All voids after this line.

    public void addLineAtScore(String entry, String teamName, String line, int score) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.addEntry(entry);
        String colouredLine = ChatColor.translateAlternateColorCodes('&', line);
        team.setPrefix(colouredLine);

        while(teamEntries.get(entry) != null) {
            entry += "" + ChatColor.GRAY;
        }

        teamEntries.put(entry, teamName);
        objective.getScore(entry).setScore(score);
    }

    public void changeLine(String teamName, String line) {
        Objects.requireNonNull(scoreboard.getTeam(teamName)).setPrefix(line);
    }

    public void setScoreAtLine(String name, int score) {
        Score playerScore = objective.getScore(name);
        playerScore.setScore(score);
    }

    public void setScoreboardTitle(String name) {
        objective.setDisplayName(name);
    }

    public void addTeamWithColour(String teamName, ChatColor colour) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setColor(colour);
    }

    public void addPlayerToTeam(String teamName, Player player) {
        Objects.requireNonNull(scoreboard.getTeam(teamName)).addEntry(player.getName());
    }

    public void changeTeamPrefix(String teamName, String prefix) {
        Objects.requireNonNull(scoreboard.getTeam(teamName)).setPrefix(prefix);
    }

    public void removePlayerFromTeam(String teamName, Player player) {
        Objects.requireNonNull(scoreboard.getTeam(teamName)).removeEntry(player.getName());
    }

    public void removeTeam(String teamName) {
        Objects.requireNonNull(scoreboard.getTeam(teamName)).unregister();
    }

    public void clearLines() {
        for(String string : scoreboard.getEntries()) {
            scoreboard.resetScores(string);
        }

        for(String teamName : teamEntries.values()) {
            Objects.requireNonNull(scoreboard.getTeam(teamName)).unregister();
        }

        teamEntries.clear();
    }

    // line doesn't have to be exact value.
    public void clearLine(String line) {
        for(String string : teamEntries.keySet()) {
            if(string.contains(line)) {
                scoreboard.resetScores(string);
            }
        }
    }

    // NOT THE SAME AS 'clearLine'.
    public void resetScore(String line) {
        scoreboard.resetScores(line);
    }

    public void sendActionBar(String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void sendTitleSubtitle(String title, String subtitle) {
        player.sendTitle(title, subtitle, 20, 20, 20);
    }

    public void sendTitle(String title) {
        player.sendTitle(title, "", 20, 20, 20);
    }

    public void sendTitleSubtitleTime(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public void sendTitleTime(String title, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, "", fadeIn, stay, fadeOut);
    }

    /**
     * @deprecated This doesn't animate anything and will do the EXACT same thing as adding a line but with no animations. It still needs to be figured out.
     */
    @Deprecated
    public void addLineAtScoreAnimated(String entry, String teamName, String line, ChatColor changeTo, int score) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.addEntry(entry);
        String colouredLine = ChatColor.translateAlternateColorCodes('&', line);
        team.setPrefix(colouredLine);

        while(teamEntries.get(entry) != null) {
            entry += "" + ChatColor.GRAY;
        }

        teamEntries.put(entry, teamName);
        objective.getScore(entry).setScore(score);

        //AnimateScoreboard animateScoreboard = new AnimateScoreboard(this, teamName, line, changeTo);

        //animateScoreboard.runTaskTimer(plugin, 0L, 20L);
    }

    /**
     * @deprecated Should be avoided as it never should be used but in the event that it needs to be (which I am unaware of) then it can be used.
     */
    @Deprecated
    public void fullyUpdateScoreboard() {
        this.player.setScoreboard(scoreboard);
    }
}
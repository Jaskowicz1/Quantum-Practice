package me.jaskowicz.quantumpractice;

import me.jaskowicz.quantumpractice.Utils.Arena;
import me.jaskowicz.quantumpractice.Utils.Game;
import me.jaskowicz.quantumpractice.Utils.Kit;
import me.jaskowicz.quantumpractice.Utils.User;
import me.jaskowicz.quantumpractice.UtilsExtra.ClassUtils;
import me.jaskowicz.quantumpractice.UtilsExtra.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class Quantumpractice extends JavaPlugin {

    public static HashMap<UUID, User> USERS = new HashMap<>();
    public static HashMap<Arena, Game> GAMES = new HashMap<>();
    public static HashMap<String, Arena> ARENAS = new HashMap<>();
    public static HashMap<String, Arena> ARENASINUSE = new HashMap<>();
    public static HashMap<String, Arena> ARENASNOTINUSE = new HashMap<>();
    public static HashMap<String, Kit> KITS = new HashMap<>();

    public static Connection connection;

    public static String prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "Quantum-Practice" + ChatColor.GRAY + "] » ";
    public static String preprefix = ChatColor.AQUA + "Quantum-Practice";

    @Override
    public void onEnable() {

        if(!this.getDataFolder().exists()) {
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: No config.yml found. Creating...");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            boolean created = this.getDataFolder().mkdirs();
            this.saveDefaultConfig();
            this.saveConfig();
            if(created) {
                Bukkit.getConsoleSender().sendMessage(" ");
                Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Config.yml created successfully!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            } else {
                Bukkit.getConsoleSender().sendMessage(" ");
                Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config.yml failed to be created.");
                Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            }
        }

        File arenasfolder = new File(getDataFolder(), File.separator + "arenas");
        if(!arenasfolder.exists()) {
            getLogger().info("Error: No arenas folder was found! Creating...");
            arenasfolder.mkdirs();
            getLogger().info("The arenas folder was successfully created!");
        }

        loadAllListeners();
        loadAllCommands();
        if(getConfig().getBoolean("useMySQL")) {
            loadDatabase();
        } else {

            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MySQL is disabled, using file alternative.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");

            File userfolder = new File(getDataFolder(), File.separator + "players");
            if(!userfolder.exists()) {
                getLogger().info("Error: No players folder was found! Creating...");
                userfolder.mkdirs();
                getLogger().info("The players folder was successfully created!");
            }
        }

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Quantum-Practice was successfully enabled.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Developed by Archie Jaskowicz.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadAllArenas() {

        File arenasfolder = new File(getDataFolder(), File.separator + "arenas");

        for(File file : arenasfolder.listFiles()) {
            File filetoload = new File(getDataFolder() + File.separator + "arenas", file.getName());

            FileConfiguration arenaData = YamlConfiguration.loadConfiguration(filetoload);


            String arenaName = arenaData.getString("arena.name");
            String arenaCreator = arenaData.getString("arena.createdBy");

            Arena arena = new Arena(arenaName);

            arena.setCreatedBy(arenaCreator);

            if(arenaData.get("arena.spawnpoint.one") instanceof Location) {
                arena.setSpawnpoint1((Location) arenaData.get("arena.spawnpoint.one"));
            }

            if(arenaData.get("arena.spawnpoint.two") instanceof Location) {
                arena.setSpawnpoint2((Location) arenaData.get("arena.spawnpoint.two"));
            }

            ARENAS.put(arenaName, arena);
            ARENASNOTINUSE.put(arenaName, arena);
        }

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        Bukkit.getConsoleSender().sendMessage("" + ChatColor.YELLOW + ARENAS.size() + " arenas were found and added.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
    }

    private void loadAllCommands() {

        try {
            ClassUtils.registerAllCommands();

            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Registered all commands.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAllListeners() {

        try {
            ClassUtils.registerAllListeners();

            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Registered all listeners.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadDatabase() {
        DatabaseUtils databaseUtils = new DatabaseUtils();

        try {
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading SQL.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            databaseUtils.openConnection();
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SQL loaded successfully.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL failed to load.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "|-------------- " + preprefix + ChatColor.WHITE + " --------------|");
        }
    }
}

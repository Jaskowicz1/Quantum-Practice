package me.jaskowicz.quantumpractice.Utils;

import org.bukkit.Location;

public class Arena {

    private final String arenaName;

    private String createdBy;

    private Location spawnpoint1;
    private Location spawnpoint2;

    public Arena(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getArenaName() {
        return arenaName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Location getSpawnpoint1() {
        return spawnpoint1;
    }

    public Location getSpawnpoint2() {
        return spawnpoint2;
    }


    // All voids after this line.


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setSpawnpoint1(Location spawnpoint1) {
        this.spawnpoint1 = spawnpoint1;
    }

    public void setSpawnpoint2(Location spawnpoint2) {
        this.spawnpoint2 = spawnpoint2;
    }
}

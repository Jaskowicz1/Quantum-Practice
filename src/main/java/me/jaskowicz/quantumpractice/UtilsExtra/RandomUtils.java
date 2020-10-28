package me.jaskowicz.quantumpractice.UtilsExtra;

import me.jaskowicz.quantumpractice.Quantumpractice;
import me.jaskowicz.quantumpractice.Utils.Arena;
import me.jaskowicz.quantumpractice.Utils.Game;
import me.jaskowicz.quantumpractice.Utils.User;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static User getRandomUser() {
        Random generator = new Random();
        Object[] users = Quantumpractice.USERS.values().toArray();
        return (User) users[generator.nextInt(users.length)];
    }

    public static User getRandomUserNotSelf(User u) {
        Random generator = new Random();
        Object[] users = Quantumpractice.USERS.values().toArray();
        User user = (User) users[generator.nextInt(users.length)];

        while(user == u) {
            user = (User) users[generator.nextInt(users.length)];
        }

        return user;
    }

    public static Game getRandomGame() {
        Random generator = new Random();
        Object[] games = Quantumpractice.GAMES.values().toArray();
        return (Game) games[generator.nextInt(games.length)];
    }

    public static Arena getRandomArena() {
        Random generator = new Random();
        Object[] arenas = Quantumpractice.ARENAS.values().toArray();
        return (Arena) arenas[generator.nextInt(arenas.length)];
    }

    public static Object getRandomObject(Object[] objects) {
        Random generator = new Random();
        return objects[generator.nextInt(objects.length)];
    }
}

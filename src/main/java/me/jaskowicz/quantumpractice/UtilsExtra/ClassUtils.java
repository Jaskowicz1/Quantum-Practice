package me.jaskowicz.quantumpractice.UtilsExtra;

import com.google.common.reflect.ClassPath;
import me.jaskowicz.quantumpractice.Quantumpractice;
import org.bukkit.Bukkit;

import java.io.IOException;

public class ClassUtils {

    public static void registerAllCommands() throws IOException {

        ClassPath cp = ClassPath.from(ClassUtils.class.getClassLoader());
        cp.getTopLevelClassesRecursive("me.jaskowicz.quantumpractice.Commands").forEach(classInfo -> {
            try {
                Class c = Class.forName(classInfo.getName());
                Object obj = c.newInstance();
                if(obj instanceof CommandExec) {
                    CommandExec commandExecutor = (CommandExec) obj;
                    Quantumpractice.getPlugin(Quantumpractice.class).getCommand(commandExecutor.name()).setExecutor(commandExecutor);
                    if(commandExecutor.otherNames() != null) {
                        for (String name : commandExecutor.otherNames()) {
                            Quantumpractice.getPlugin(Quantumpractice.class).getCommand(name).setExecutor(commandExecutor);
                        }
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    public static void registerAllListeners() throws IOException {

        ClassPath cp = ClassPath.from(ClassUtils.class.getClassLoader());
        cp.getTopLevelClassesRecursive("me.jaskowicz.blockparty.Listeners").forEach(classInfo -> {
            try {
                Class c = Class.forName(classInfo.getName());
                Object obj = c.newInstance();
                if(obj instanceof ListenerExec) {
                    ListenerExec listenerExec = (ListenerExec) obj;
                    if(listenerExec.execute()) {
                        Bukkit.getPluginManager().registerEvents(listenerExec, Quantumpractice.getPlugin(Quantumpractice.class));
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }
}

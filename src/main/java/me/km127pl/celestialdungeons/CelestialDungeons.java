package me.km127pl.celestialdungeons;

import co.aikar.commands.PaperCommandManager;
import me.km127pl.celestialdungeons.commands.BaseDungeonCommand;
import me.km127pl.celestialdungeons.commands.PartyDungeonCommand;
import me.km127pl.celestialdungeons.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class CelestialDungeons extends JavaPlugin {

    private static Logger logger;

    @Override
    public void onEnable() {
        logger = this.getLogger();

        log("Starting up [1/5] - Saving default files");
        this.saveDefaultConfig();

        log("Registering [2/5] - Commands");

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new BaseDungeonCommand());
        manager.registerCommand(new PartyDungeonCommand());

        log("Registering [3/5] - Events");

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);

        log("Loading [4/5] - Dungeons");

        log("Finishing up [5/5] - Cleaning leftovers");
        //TODO:unload stuff that is not neeeded anymore
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void log(String message) {
        logger.info(message);
    }
}

package me.km127pl.celestialdungeons;

import co.aikar.commands.PaperCommandManager;
import me.km127pl.celestialdungeons.commands.BaseDungeonCommand;
import me.km127pl.celestialdungeons.commands.PartyDungeonCommand;
import me.km127pl.celestialdungeons.listeners.PlayerListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CelestialDungeons extends JavaPlugin {

    private static Logger logger;
    public static MiniMessage mm;

    public static void log(String message) {
        logger.info(message);
    }

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

        log("Loading [4/5] - Dungeons and such");
        CelestialDungeons.mm = MiniMessage.miniMessage();

        log("Finishing up [5/5] - Cleaning leftovers");
        //TODO:unload stuff that is not neeeded anymore
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Component parse(String message) {
        return CelestialDungeons.mm.deserialize(message).asComponent();
    }

    public static void message(String message, Player player) {
        player.sendMessage(parse(message));
    }
}

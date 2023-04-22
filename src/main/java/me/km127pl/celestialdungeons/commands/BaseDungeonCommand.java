package me.km127pl.celestialdungeons.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.dungeons.DungeonCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("dungeon|celestialdungeons|cd")
public class BaseDungeonCommand extends BaseCommand {

    @Dependency
    public CelestialDungeons plugin;

    @Default
    @Subcommand("help")
    @CommandCompletion("help|create|locate|teleport|list|reload")
    @HelpCommand
    public void onHelpCommand(Player player) {
        //TODO: Replace with AdventureAPIs' Components
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRunning &eCelestialDungeons &aversion " + plugin.getPluginMeta().getVersion()));
    }

    @Subcommand("create")
    @CommandPermission("celestialdungeons.create|celestialdungeons.admin")
    public void onCreateCommand(Player player) {
        DungeonCreator creator = new DungeonCreator();
        creator.createDungeonAt(player.getLocation());
    }

    @Subcommand("locate")
    @CommandPermission("celestialdungeons.locate|celestialdungeons.admin")
    public void onLocateCommand(Player player) {
    }

    @Subcommand("teleport")
    @CommandPermission("celestialdungeons.teleport|celestialdungeons.admin")
    public void onTeleportCommand(Player player) {
    }

    @Subcommand("reload")
    @CommandPermission("celestialdungeons.reload|celestialdungeons.admin")
    public void onReloadCommand(Player player) {
        //TODO:Reload dungeons
        plugin.reloadConfig();
    }

    @Subcommand("list")
    public void onListCommand(Player player) {
    }

}

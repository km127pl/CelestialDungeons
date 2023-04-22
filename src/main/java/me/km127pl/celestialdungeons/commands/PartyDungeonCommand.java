package me.km127pl.celestialdungeons.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.party.PartyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("party")
public class PartyDungeonCommand extends BaseCommand {

    @Dependency
    public CelestialDungeons plugin;

    @HelpCommand
    @Default
    @CommandCompletion("create")
    public void onMainPartyCommand(Player player) {
        //TODO:Show help message
    }

    @Subcommand("create")
    public void onPartyCreate(Player player) {
        boolean created = PartyManager.createParty(player);
        //TODO:Replace with AdventureAPI's Components
        if (created) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aParty created successfully!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou already have a party!"));
        }
    }
}

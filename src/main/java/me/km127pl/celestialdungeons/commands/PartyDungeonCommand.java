package me.km127pl.celestialdungeons.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.party.PartyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("party")
public class PartyDungeonCommand extends BaseCommand {

    @Dependency
    public CelestialDungeons plugin;

    @HelpCommand
    @Default
    @CommandCompletion("create|list|disband|invite")
    public void onMainPartyCommand(Player player) {
        //TODO:Show help message
    }

    @HelpCommand
    @Default
    @CommandCompletion("create|list|disband|invite|@players")
    public void onMainPartyCommand(Player player, Player toInvite) {
        onPartyInvite(player, toInvite); // /party <player> alias of /party invite <player>
    }

    @Subcommand("invite")
    public void onPartyInvite(Player player, Player toInvite) {
        if (PartyManager.hasParty(player)) {
            PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
            if (party.isOwner(player.getUniqueId())) {

                return;
            }
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have a party!"));

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

    @Subcommand("list")
    public void onPartyList(Player player) {
        if (PartyManager.hasParty(player)) {
            PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
            player.sendMessage(Component.text("Your party: "));
            party.members.forEach((member) -> {
                //TODO:this probably uses more resources than it should
                Player memb = Bukkit.getPlayer(member);
                player.sendMessage(Component.text(String.valueOf(memb.getName())));
            });
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have a party!"));
        }
    }

    @Subcommand("disband")
    public void onPartyDisband(Player player) {
        if (PartyManager.hasParty(player)) {
            PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
            if (party.isOwner(player.getUniqueId())) {
                PartyManager.disbandParty(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour party has been disbanded!"));

                return;
            }
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have a party!"));

    }
}

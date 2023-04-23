package me.km127pl.celestialdungeons.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.party.PartyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("party")
public class PartyDungeonCommand extends BaseCommand {

    @Dependency
    public CelestialDungeons plugin;

    @HelpCommand
    @Default
    @CommandCompletion("create|list|disband|kick|invite")
    public void onMainPartyCommand(Player player) {
        //TODO:Show help message
    }

    @HelpCommand
    @Default
    @CommandCompletion("create|list|disband|invite|kick|@players")
    public void onMainPartyCommand(Player player, OnlinePlayer toInvite) {
        onPartyInvite(player, toInvite); // /party <player> alias of /party invite <player>
    }



    @Subcommand("invite")
    @CommandCompletion("accept|deny|@players")
    public void onPartyInvite(Player player, OnlinePlayer toInvite) {
        if (player.equals(toInvite.player)) {
            CelestialDungeons.message( "<red>You can't invite yourself! :(", player);
            return;
        }
        Player invitee = toInvite.getPlayer();
        if (PartyManager.hasParty(player)) {
            PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
            if (party.isOwner(player.getUniqueId())) {
                if (!PartyManager.hasParty(invitee)) {
                    CelestialDungeons.message("<yellow>" + player.getName() + "<green> is inviting you to their party!" +
                            "<newline><blue><bold>[ <green><bold><click:run_command:/party invite accept>ACCEPT</click> <gray>|</gray> <red><bold><click:run_command:/party invite deny>DENY</click> <blue><bold>]", invitee);
                    PartyManager.createInvite(player, invitee, party);
                    CelestialDungeons.message("<yellow>" + invitee.getName() + "<green> has been invited to your party!", player);

                    return;
                } else {
                    CelestialDungeons.message("<red>That player already has a party!", player);
                    return;
                }
            }
        }
        CelestialDungeons.message("<red>You don't have a party!", player);
    }

    @Subcommand("invite accept")
    public void onInviteAccept(Player player) {
        boolean result = PartyManager.acceptInvite(player);
        if (result) {
            CelestialDungeons.message("<red>You do not have any pending invites!", player);
        } else {
            CelestialDungeons.message("<green>You have accepted an invite.", player);
        }
    }

    @Subcommand("invite deny")
    public void onInviteDeny(Player player) {
        boolean result = PartyManager.denyInvite(player);
        if (result) {
            CelestialDungeons.message("<red>You do not have any pending invites!", player);
        } else {
            CelestialDungeons.message("<red>You have denied an invite.", player);
        }
    }


    @Subcommand("create")
    public void onPartyCreate(Player player) {
        boolean created = PartyManager.createParty(player);

        if (created) {
            CelestialDungeons.message("<green>You have created a party!", player);
        } else {
            CelestialDungeons.message("<red>You already have a party!", player);
        }
    }

    @Subcommand("list")
    public void onPartyList(Player player) {
        if (PartyManager.hasParty(player)) {
            PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
            CelestialDungeons.message("<green>Your party: ", player);
            party.members.forEach((member) -> {
                Player membr = Bukkit.getPlayer(member);
                if ((membr != null ? membr.getName() : null) == null) return;

                if (member != player.getUniqueId()) {
                    CelestialDungeons.message("<green>- <yellow>" + membr.getName() +
                            " <red><bold><click:run_command:/party kick " + membr.getName() + "> KICK</click>", player);
                } else {
                    CelestialDungeons.message("<green>- <yellow>" + Bukkit.getPlayer(member).getName(), player);
                }
            });
        } else {
            CelestialDungeons.message("<red>You don't have a party!", player);
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

    @Subcommand("kick")
    @CommandCompletion("@players")
    public void onPartyKick(Player player, OnlinePlayer toKick) {
        boolean result = true;
        Player kick = toKick.getPlayer();
        UUID uuid = kick.getUniqueId();

        PartyManager.Party party = PartyManager.parties.get(player.getUniqueId());
        if (party.has(uuid)) {
            party.removeMember(uuid);
            party.broadcast("<yellow>" + kick.getName() + " <red>has been kicked from the party");
            CelestialDungeons.message("<red>You have been kicked from the party", kick);
        }
    }
}

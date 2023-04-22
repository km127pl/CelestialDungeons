package me.km127pl.celestialdungeons.listeners;


import me.km127pl.celestialdungeons.party.PartyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PartyManager.disbandParty(player); //TODO:Replace with a random owner if there's more players
        //TODO:Notify members of party owner change/leave
    }
}

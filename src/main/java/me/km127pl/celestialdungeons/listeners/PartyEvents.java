package me.km127pl.celestialdungeons.listeners;

import me.km127pl.celestialdungeons.events.PlayerJoinPartyEvent;
import me.km127pl.celestialdungeons.events.PlayerQuitPartyEvent;
import me.km127pl.celestialdungeons.tablist.PartyTablist;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PartyEvents implements Listener {
	@EventHandler
	public void onPlayerJoinParty(PlayerJoinPartyEvent event) {
        PartyTablist tablist = PartyTablist.getOrCreate(event.getParty());
        tablist.display();
	}

	@EventHandler
	public void onPlayerQuitParty(PlayerQuitPartyEvent event) {
		PartyTablist tablist = PartyTablist.getOrCreate(event.getParty());
		tablist.display();
		event.getPlayer().sendPlayerListHeaderAndFooter(Component.empty(), Component.empty()); // clear the tablist
	}
}

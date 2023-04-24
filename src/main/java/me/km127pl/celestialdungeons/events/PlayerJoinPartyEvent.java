package me.km127pl.celestialdungeons.events;

import me.km127pl.celestialdungeons.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerJoinPartyEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private final PartyManager.Party party;
	private boolean isCancelled;

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public PlayerJoinPartyEvent(Player player, PartyManager.Party party) {
		this.player = player;
		this.party = party;
	}

	public Player getPlayer() {
		return player;
	}

	public PartyManager.Party getParty() {
		return party;
	}

	public UUID getPartyOwner() {
		return party.getOwner();
	}

	public static void fire(Player player, PartyManager.Party party) {
		Bukkit.getPluginManager().callEvent(new PlayerJoinPartyEvent(player, party));
	}
}

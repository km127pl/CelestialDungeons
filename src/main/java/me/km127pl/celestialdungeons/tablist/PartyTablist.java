package me.km127pl.celestialdungeons.tablist;

import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.party.PartyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PartyTablist {
	private PartyManager.Party party;

	public static HashMap<UUID, PartyTablist> tablists = new HashMap<>();
	public PartyTablist(PartyManager.Party party) {
		this.party = party;
		tablists.put(party.getOwner(), this);
	}

	public void update() {
		this.party = PartyManager.parties.get(this.party.owner);
		this.display();
	}

	public static PartyTablist getOrCreate(PartyManager.Party party) {
		PartyTablist tablist;
		if (tablists.containsKey(party.getOwner())) {
			tablist = tablists.get(party.getOwner());
		} else {
			tablist = new PartyTablist(party);
		}
		return tablist;
	}

	public void update(PartyManager.Party party) {
		this.party = party;
		this.display();
	}

	public void display() {
		Component header = CelestialDungeons.parse("<newline><aqua><bold>            CelestialDungeons            <newline>");
		Component footer;
		if (this.party.members.size() > 1) {
			footer = CelestialDungeons.parse("<newline><yellow>You are currently in a party<newline><yellow>with <green>" + this.party.members.size() + "<yellow> members");
		} else {
			footer = CelestialDungeons.parse("<newline><yellow>You are currently <green>alone<yellow> in a party");
		}

		footer = footer.append(CelestialDungeons.parse("<newline><yellow>Type <green>/dungeon start<newline><yellow>to start a dungeon!<newline>"));

		Component finalFooter = footer;

		this.party.members.forEach((member) -> {
			Objects.requireNonNull(Bukkit.getPlayer(member)).sendPlayerListHeaderAndFooter(header, finalFooter);
		});
	}
}

package me.km127pl.celestialdungeons.party;

import me.km127pl.celestialdungeons.CelestialDungeons;
import me.km127pl.celestialdungeons.events.PlayerJoinPartyEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PartyManager {

    public static HashMap<UUID, Party> parties = new HashMap<>();
    public static HashMap<UUID, PartyInvite> invites = new HashMap<>();

    public static boolean createParty(Player owner) {
        if (parties.containsKey(owner.getUniqueId())) return false;
        parties.put(
                owner.getUniqueId(),
                new Party(owner.getUniqueId())
        );
        return true;
    }

    public static boolean disbandParty(Player owner) {
        //TODO:Notify members
        if (!parties.containsKey(owner.getUniqueId())) return false;
        Party party = parties.get(owner.getUniqueId());
        party.members.forEach((member) -> {
            if(member != owner.getUniqueId())
                CelestialDungeons.message("<red>The party you were been just got disbanded!", Objects.requireNonNull(Bukkit.getPlayer(member)));
        });
        parties.remove(
                owner.getUniqueId()
        );
        return true;
    }

    public static PartyInvite createInvite(Player owner, Player invitee, Party party) {
        PartyInvite invite = new PartyInvite(owner.getUniqueId(), invitee.getUniqueId(), party);

        invites.put(invitee.getUniqueId(), invite);
        return invite;
    }

    /**
     * Accepts an invitation
     * @param invitee the person who was invited
     * @return if there were any errors during the invite
     */
    public static boolean acceptInvite(Player invitee) {
        PartyInvite invite = invites.get(invitee.getUniqueId());
        if (invite == null || Date.from(Instant.now()).getTime() > invite.expirationTime + invite.getCreatedAt().getTime() || hasParty(invitee)) {
            return true; // it has now expired
        }
        invite.getParty().addMember(invitee.getUniqueId());
        invite.getParty().broadcast("<yellow>" + invitee.getName() + "<green> has joined the party!");
        denyInvite(invitee); // remove the invite after it has been accepted
        PlayerJoinPartyEvent.fire(invitee, invite.getParty());
        return false; // no errors
    }

    /**
     * Denies an invitation
     * @param invitee the person who was invited
     * @return if there were any errors during the invite
     */
    public static boolean denyInvite(Player invitee) {
        if (invites.containsKey(invitee.getUniqueId())) {
            invites.remove(invitee.getUniqueId(), invites.get(invitee.getUniqueId()));
            return false;
        }
        return true;
    }

    public static boolean addPlayer(Player owner, Player member) {
        if (!parties.containsKey(owner.getUniqueId())) return false;

        parties.get(owner.getUniqueId()).addMember(member.getUniqueId());
        return true;
    }

    public static boolean hasParty(Player player) {
        AtomicBoolean has = new AtomicBoolean(false);

        parties.forEach((uuid, party) -> {
            if (party.has(player.getUniqueId())) {
                has.set(true);
            }
        });

        return has.get();
    }

    public static Party getParty(Player player) {
        AtomicReference<Party> _party = new AtomicReference<>();

        parties.forEach((uuid, party) -> {
            if (party.has(player.getUniqueId())) {
                _party.set(party);
            }
        });
        return _party.get();
    }

    public static class PartyInvite {
        public Date createdAt;
        public UUID owner;
        public UUID invitee;
        public Party party;
        // 30 seconds
        public final long expirationTime = 1000*30;

        public PartyInvite(UUID owner, UUID invitee, Party party) {
            this.createdAt = Date.from(Instant.now());
            this.owner = owner;
            this.invitee = invitee;
            this.party = party;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public UUID getOwner() {
            return owner;
        }

        public UUID getInvitee() {
            return invitee;
        }

        public Party getParty() {
            return party;
        }

    }

    public static class Party {
        public Date createdAt;
        public UUID owner;
        public ArrayList<UUID> members;

        public Party(Player player) {
            new Party(player.getUniqueId());
        }

        public Party(UUID ownerUUID) {
            this.owner = ownerUUID;
            this.members = new ArrayList<>();
            // the owner should also be a member of the party
            this.members.add(ownerUUID);
            this.createdAt = Date.from(Instant.now());
        }

        public void broadcast(String message) {
            Component component = CelestialDungeons.parse(message);
            this.members.forEach((member) -> Objects.requireNonNull(Bukkit.getPlayer(member)).sendMessage(component));
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public boolean has(UUID player) {
            return members.contains(player);
        }

        public boolean isOwner(UUID player) {
            return this.owner == player;
        }

        public UUID getOwner() {
            return owner;
        }

        public void setOwner(UUID owner) {
            this.owner = owner;
        }

        public ArrayList<UUID> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<UUID> members) {
            this.members = members;
        }

        public boolean addMember(UUID member) {
            if (members.contains(member)) return false;
            //TODO:Check for max members
            members.add(member);
            return true;
        }

        public boolean removeMember(UUID member) {
            if (!members.contains(member)) return false;
            //TODO:Check for max members
            members.remove(member);
            return true;
        }

        @Override
        public String toString() {
            return "Party{" +
                    "createdAt=" + createdAt +
                    ", owner=" + owner +
                    ", members=" + members +
                    '}';
        }
    }
}

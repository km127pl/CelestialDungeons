package me.km127pl.celestialdungeons.party;

import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class PartyManager {

    public static HashMap<UUID, Party> parties = new HashMap<>();

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
        parties.remove(
                owner.getUniqueId()
        );
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

package me.km127pl.celestialdungeons.dungeons;

import org.bukkit.Location;
import org.bukkit.Material;

public class DungeonCreator {
    public void createDungeonAt(Location location) {
        location.getBlock().setType(Material.STONE);
    }
}

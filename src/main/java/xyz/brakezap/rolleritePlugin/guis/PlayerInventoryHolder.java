package xyz.brakezap.rolleritePlugin.guis;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class PlayerInventoryHolder implements InventoryHolder {
    private final Inventory playerInventory;
    private final Player owner;

    public PlayerInventoryHolder(Player p) {
        this.owner = p;
        this.playerInventory = Bukkit.getServer().createInventory(this, InventoryType.PLAYER,
                text(p.getName()).color(NamedTextColor.DARK_RED).toBuilder().build());
        this.playerInventory.setContents(p.getInventory().getContents());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.playerInventory;
    }

    public Player getOwner() {
        return this.owner;
    }
}

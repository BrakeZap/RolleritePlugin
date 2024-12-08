package xyz.brakezap.rolleritePlugin.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class TrashInventory implements InventoryHolder {
    private final Inventory inventory;

    public TrashInventory(Player p) {
        this.inventory = Bukkit.createInventory(this, 27, text("Trash").toBuilder().build());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}

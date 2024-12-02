package xyz.brakezap.rolleritePlugin.guis;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class TrashInventory implements InventoryHolder {
    private final Inventory inventory;

    public TrashInventory(Player p) {
        this.inventory = Bukkit.createInventory(p, 27, text("Trash").color(NamedTextColor.GREEN).toBuilder().build());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}

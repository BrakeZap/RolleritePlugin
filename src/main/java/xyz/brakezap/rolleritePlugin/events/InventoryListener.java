package xyz.brakezap.rolleritePlugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import xyz.brakezap.rolleritePlugin.guis.PlayerInventoryHolder;

public class InventoryListener implements Listener {
    @EventHandler
    public void onPlayerInventoryClose(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder(false) instanceof PlayerInventoryHolder))
            return;

        PlayerInventoryHolder closedInvHolder = (PlayerInventoryHolder) e.getInventory().getHolder(false);
        Inventory closedInv = closedInvHolder.getInventory();
        Inventory playerInv = closedInvHolder.getOwner().getInventory();

        playerInv.setContents(closedInv.getContents());
        closedInvHolder.getOwner().updateInventory();
    }
}

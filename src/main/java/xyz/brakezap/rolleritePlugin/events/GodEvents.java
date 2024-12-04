package xyz.brakezap.rolleritePlugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import xyz.brakezap.rolleritePlugin.commands.GodCommand;

public class GodEvents implements Listener {

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        if (GodCommand.hasGodMode.contains((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHungerEvent(FoodLevelChangeEvent e){
        if (!(e.getEntity() instanceof Player)) return;

        if (GodCommand.hasGodMode.contains((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }
}

package xyz.brakezap.rolleritePlugin;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.brakezap.rolleritePlugin.commands.TrashCommand;
import xyz.brakezap.rolleritePlugin.events.InventoryListener;

public final class RolleritePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("trash", "Open a trash menu.", new TrashCommand());
        });
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}


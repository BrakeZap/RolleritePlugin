package xyz.brakezap.rolleritePlugin;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.brakezap.rolleritePlugin.commands.*;
import xyz.brakezap.rolleritePlugin.events.GodEvents;
import xyz.brakezap.rolleritePlugin.events.InventoryListener;

public final class RolleritePlugin extends JavaPlugin {

    public static RolleritePlugin instance;

    private YamlConfiguration langFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("trash", "Open a trash menu.", new TrashCommand());
            commands.register("fix", "Fix the item in your hand.", new FixCommand());
            commands.register("god", "Give a player god mode.", new GodCommand());
            commands.register("enderchest", "Open a player's enderchest for them.", new EChestCommand());
            commands.register("gamemode", "Set a player's gamemode.", new GamemodeCommand());
            commands.register("tpa", "Send a teleport request to the specified player.", new TPACommand());
            commands.register("openinv", "Open a player's inventory to view or edit it.", new OpenInvCommand());
        });
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new GodEvents(), this);
        instance = this;

        loadLang();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadLang() {
        File file = new File(this.getDataFolder(), "lang.yml");

        langFile = YamlConfiguration.loadConfiguration(file);
        InputStreamReader reader = new InputStreamReader(getResource("lang.yml"));
        YamlConfiguration defaultLang = YamlConfiguration.loadConfiguration(reader);
        langFile.setDefaults(defaultLang);
        langFile.options().copyDefaults(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    langFile.save(file);
                } catch (IOException e) {
                    getLogger().warning("Unable to save the lang file!");
                }
            }
        }.runTaskAsynchronously(this);

    }

    public YamlConfiguration getLangConfig() {
        return this.langFile;
    }

    public String getLangMessage(String configPath) {
        return ChatColor.translateAlternateColorCodes('&', langFile.getString(configPath));
    }
}

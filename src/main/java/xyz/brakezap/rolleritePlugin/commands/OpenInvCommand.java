package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.List;

import xyz.brakezap.rolleritePlugin.guis.PlayerInventoryHolder;

import java.util.Collection;

public class OpenInvCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage("Cannot execute openinv command using console!");
            return;
        }

        if (args.length == 0 || args.length > 1) {
            commandSourceStack.getSender().sendMessage("/openinv <player>");
            return;
        }

        Player p = (Player) commandSourceStack.getSender();

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            p.sendMessage("That player is not online!");
            return;
        }

        if (target.getName().equalsIgnoreCase(p.getName())) {
            p.sendMessage("You can't open your own inventory!");
            return;
        }

        // TODO: Locale here
        p.openInventory(new PlayerInventoryHolder(target).getInventory());
    }

    @Override
    public @Nullable String permission() {
        return "RP.openinv";
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return List.of();
    }

}

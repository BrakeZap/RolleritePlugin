package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import xyz.brakezap.rolleritePlugin.RolleritePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class EChestCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player) && args.length == 0) {
            commandSourceStack.getSender().sendMessage("/enderchest <player>");
            return;
        }

        if (!(commandSourceStack.getSender() instanceof Player) && args.length > 1) {
            commandSourceStack.getSender().sendMessage("/enderchest <player>");
            return;
        }

        if (args.length > 1) {
            commandSourceStack.getSender().sendMessage("/enderchest [player]");
            return;
        }

        CommandSender p = commandSourceStack.getSender();

        if (args.length == 0) {
            Player player = (Player) p;
            Inventory eChest = player.getEnderChest();
            player.openInventory(eChest);
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage(RolleritePlugin.instance.getLangMessage("player-not-online-message"));
            return;
        }

        p.sendMessage(RolleritePlugin.instance.getLangMessage("echest-command-open-for-message") + target.getName());
        if (!p.getName().equalsIgnoreCase(target.getName()))
            target.sendMessage(
                    RolleritePlugin.instance.getLangMessage("echest-command-open-for-message") + target.getName());

        Inventory eChest = target.getEnderChest();
        target.openInventory(eChest);
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return List.of();
    }

    @Override
    public @Nullable String permission() {
        return "RP.echest";
    }
}

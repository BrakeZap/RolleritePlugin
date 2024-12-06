package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
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
            //TODO: LOCALE
            commandSourceStack.getSender().sendMessage("/enderchest [player]");
            return;
        }

        CommandSender p = commandSourceStack.getSender();


        if (args.length == 0) {
            Player player = (Player) p;
            Inventory eChest = player.getEnderChest();
            player.openInventory(eChest);
            //TODO: LOCALE
            p.sendMessage("Opening enderchest...");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            //TODO: LOCALE
            p.sendMessage("That player is not online!");
            return;
        }


        //TODO: LOCALE
        p.sendMessage("Enderchest opened for " + target.getName() + "!");
        if (!p.getName().equalsIgnoreCase(target.getName()))
            target.sendMessage("Enderchest opened for " + target.getName() + "!");

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

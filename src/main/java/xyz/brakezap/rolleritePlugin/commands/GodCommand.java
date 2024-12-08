package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import xyz.brakezap.rolleritePlugin.RolleritePlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class GodCommand implements BasicCommand {

    public static HashSet<CommandSender> hasGodMode = new HashSet<>();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player) && args.length == 0) {
            commandSourceStack.getSender().sendMessage("/god <player>");
            return;
        }

        if (!(commandSourceStack.getSender() instanceof Player) && args.length > 1) {
            commandSourceStack.getSender().sendMessage("/god <player>");
            return;
        }

        if (args.length > 1) {
            commandSourceStack.getSender().sendMessage("/god [player]");
            return;
        }

        CommandSender p = commandSourceStack.getSender();

        if (args.length == 0) {
            if (hasGodMode.contains(p)) {
                hasGodMode.remove(p);

                p.sendMessage(RolleritePlugin.instance.getLangMessage("god-command-off-message"));
            } else {
                hasGodMode.add(p);

                p.sendMessage(RolleritePlugin.instance.getLangMessage("god-command-on-message"));
            }
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage(RolleritePlugin.instance.getLangMessage("player-not-online-message"));
            return;
        }
        if (hasGodMode.contains(target)) {
            hasGodMode.remove(target);
            p.sendMessage(
                    RolleritePlugin.instance.getLangMessage("god-command-off-target-message") + target.getName() + "!");
            if (!p.getName().equalsIgnoreCase(target.getName()))
                target.sendMessage(
                        RolleritePlugin.instance.getLangMessage("god-command-off-target-message") + target.getName()
                                + "!");
        } else {
            hasGodMode.add(target);
            p.sendMessage(
                    RolleritePlugin.instance.getLangMessage("god-command-on-target-message") + target.getName() + "!");
            if (!p.getName().equalsIgnoreCase(target.getName()))
                target.sendMessage(
                        RolleritePlugin.instance.getLangMessage("god-command-on-target-message") + target.getName()
                                + "!");
        }

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
        return "RP.god";
    }
}

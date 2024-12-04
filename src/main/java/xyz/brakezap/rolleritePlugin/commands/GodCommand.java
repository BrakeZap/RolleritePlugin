package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
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
            //TODO: LOCALE
            commandSourceStack.getSender().sendMessage("/god [player]");
            return;
        }

        CommandSender p = commandSourceStack.getSender();


        if (args.length == 0) {
            if (hasGodMode.contains(p)){
                hasGodMode.remove(p);
                //TODO: LOCALE
                p.sendMessage("God mode turned off!");
            }else {
                hasGodMode.add(p);
                //TODO: LOCALE
                p.sendMessage("God mode turned on!");
            }
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            //TODO: LOCALE
            p.sendMessage("That player is not online!");
            return;
        }
        if (hasGodMode.contains(target)){
            hasGodMode.remove(target);
            //TODO: LOCALE
            p.sendMessage("God mode turned off for " + target.getName() + "!");
            if (!p.getName().equalsIgnoreCase(target.getName()))
                target.sendMessage("God mode turned off for " + target.getName() + "!");
        }else {
            hasGodMode.add(target);
            //TODO: LOCALE
            p.sendMessage("God mode turned on for " + target.getName() + "!");
            if (!p.getName().equalsIgnoreCase(target.getName()))
                target.sendMessage("God mode turned on for " + target.getName() + "!");
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

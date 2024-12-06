package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GamemodeCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player) && args.length == 0) {
            commandSourceStack.getSender().sendMessage("/gamemode <type> <player>");
            return;
        }

        if (!(commandSourceStack.getSender() instanceof Player) && args.length == 1) {
            commandSourceStack.getSender().sendMessage("/gamemode <type> <player>");
            return;
        }

        if (!(commandSourceStack.getSender() instanceof Player) && args.length > 2) {
            commandSourceStack.getSender().sendMessage("/gamemode <type> <player>");
            return;
        }
        if (args.length == 0) {
            //TODO: LOCALE
            commandSourceStack.getSender().sendMessage("/gamemode <type> [player]");
            return;
        }

        if (args.length > 2) {
            //TODO: LOCALE
            commandSourceStack.getSender().sendMessage("/gamemode <type> [player]");
            return;
        }

        CommandSender p = commandSourceStack.getSender();


        if (args.length == 1) {
            Player player = (Player) p;
            GameMode mode;
            try {
                mode = GameMode.valueOf(args[0].toUpperCase());
            }catch (IllegalArgumentException e){
                p.sendMessage("You must specify a correct gamemode!");
                return;
            }

            player.setGameMode(mode);

            //TODO: LOCALE
            p.sendMessage("Setting gamemode to: "+ mode.name().toLowerCase());
            return;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            //TODO: LOCALE
            p.sendMessage("That player is not online!");
            return;
        }

        GameMode mode;
        try {
            mode = GameMode.valueOf(args[0].toUpperCase());
        }catch (IllegalArgumentException e){
            p.sendMessage("You must specify a correct gamemode!");
            return;
        }

        //TODO: LOCALE
        p.sendMessage("Setting " + target.getName() + "'s " + "gamemode to: "+ mode.name().toLowerCase());
        if (!p.getName().equalsIgnoreCase(target.getName()))
            target.sendMessage("Setting gamemode to: "+ mode.name().toLowerCase());

        target.setGameMode(mode);
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {

        if (args.length == 1) {
            return Arrays.stream(GameMode.values()).map((gm) -> gm.name().toLowerCase()).toList();
        }

        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return List.of();
    }

    @Override
    public @Nullable String permission() {
        return "RP.gamemode";
    }
}

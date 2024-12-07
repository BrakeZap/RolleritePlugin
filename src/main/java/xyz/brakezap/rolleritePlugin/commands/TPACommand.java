package xyz.brakezap.rolleritePlugin.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import xyz.brakezap.rolleritePlugin.RolleritePlugin;
import xyz.brakezap.rolleritePlugin.utils.TPARequest;

public class TPACommand implements BasicCommand {

    private static HashSet<TPARequest> pendingRequests = new HashSet<>();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage("Console cannot use this command!");
            return;
        }

        Player p = (Player) commandSourceStack.getSender();

        if (args.length == 0 || args.length > 1) {
            // TODO: LOCALE
            p.sendMessage("/tpa <player | accept | deny>");
            return;
        }

        if (args[0].equalsIgnoreCase(TPARequest.Request.ACCEPT.name())) {
            TPARequest req = findOpenRequests(p.getUniqueId());
            if (req == null) {
                // TODO: LOCALE
                p.sendMessage("No active tpa requests!");
                return;
            }
            req.getTask().cancel();
            pendingRequests.remove(req);

            // TODO: LOCALE
            Bukkit.getPlayer(req.getSourcePlayer()).teleport(Bukkit.getPlayer(req.getTargetPlayer()));
            p.sendMessage("Accepting tpa request...");
            return;
        }

        if (args[0].equalsIgnoreCase(TPARequest.Request.DENY.name())) {
            TPARequest req = findOpenRequests(p.getUniqueId());
            if (req == null) {
                // TODO: LOCALE
                p.sendMessage("No active tpa requests!");
                return;
            }
            req.getTask().cancel();
            pendingRequests.remove(req);

            // TODO: LOCALE
            p.sendMessage("Denying tpa request...");
            Bukkit.getPlayer(req.getSourcePlayer()).sendMessage("Tpa request denied!");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            // TODO: Locale
            p.sendMessage("That player is not online!");
            return;
        }

        if (target.getUniqueId().equals(p.getUniqueId())) {
            // TODO: LOCALE
            p.sendMessage("You can't tpa to yourself!");
            return;
        }
        TPARequest req = new TPARequest(p.getUniqueId(), target.getUniqueId());

        if (hasRequested(p.getUniqueId())) {
            p.sendMessage("You already have a pending tpa request!");
            return;
        }

        var task = new BukkitRunnable() {
            @Override
            public void run() {
                pendingRequests.remove(req);
                // TODO: LOCALE
                p.sendMessage("TPA request to: " + target.getName() + " expired.");
                target.sendMessage("Tpa request from " + p.getName() + " expired.");
            }
        };
        req.setTask(task);
        task.runTaskLater(RolleritePlugin.instance, 20 * 10);
        pendingRequests.add(req);

        // TODO: ADD LOCALE

        p.sendMessage("Sending tpa request...");
        target.sendMessage("Incoming tpa request from: " + p.getName() + ". " + "Please use /tpa accept or /tpa deny.");
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {

        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("accept");
            suggestions.add("deny");
            Bukkit.getOnlinePlayers().stream().map(Player::getName).forEach((name) -> suggestions.add(name));
            return suggestions;
        }

        return List.of();
    }

    @Override
    public @Nullable String permission() {
        return "RP.tpa";
    }

    private TPARequest findOpenRequests(UUID target) {
        for (TPARequest req : pendingRequests) {
            if (req.getTargetPlayer().equals(target)) {
                return req;
            }
        }
        return null;
    }

    private boolean hasRequested(UUID source) {
        for (TPARequest req : pendingRequests) {
            if (req.getSourcePlayer().equals(source)) {
                return true;
            }
        }
        return false;
    }
}

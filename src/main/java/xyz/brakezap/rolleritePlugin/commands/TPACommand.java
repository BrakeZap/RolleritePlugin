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

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import xyz.brakezap.rolleritePlugin.RolleritePlugin;
import xyz.brakezap.rolleritePlugin.utils.TPARequest;

public class TPACommand implements BasicCommand {

    private static HashSet<TPARequest> pendingRequests = new HashSet<>();

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender()
                    .sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-console-message"));
            return;
        }

        Player p = (Player) commandSourceStack.getSender();

        if (args.length == 0 || args.length > 1) {
            p.sendMessage("/tpa <player | accept | deny>");
            return;
        }

        if (args[0].equalsIgnoreCase(TPARequest.Request.ACCEPT.name())) {
            TPARequest req = findOpenRequests(p.getUniqueId());
            if (req == null) {
                p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-no-requests-message"));
                return;
            }
            req.getTask().cancel();
            pendingRequests.remove(req);

            Bukkit.getPlayer(req.getSourcePlayer()).teleport(Bukkit.getPlayer(req.getTargetPlayer()));
            p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-accept-message"));
            return;
        }

        if (args[0].equalsIgnoreCase(TPARequest.Request.DENY.name())) {
            TPARequest req = findOpenRequests(p.getUniqueId());
            if (req == null) {
                p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-no-requests-message"));
                return;
            }
            req.getTask().cancel();
            pendingRequests.remove(req);

            p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-deny-message"));
            Bukkit.getPlayer(req.getSourcePlayer())
                    .sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-denied-message"));
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            p.sendMessage(RolleritePlugin.instance.getLangMessage("player-not-online-message"));
            return;
        }

        if (target.getUniqueId().equals(p.getUniqueId())) {
            p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-same-message"));
            return;
        }
        TPARequest req = new TPARequest(p.getUniqueId(), target.getUniqueId());

        if (hasRequested(p.getUniqueId())) {
            p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-pending-message"));
            return;
        }

        var task = new BukkitRunnable() {
            @Override
            public void run() {
                pendingRequests.remove(req);

                p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-expire-to-message")
                        + target.getName() + " expired.");
                target.sendMessage(
                        RolleritePlugin.instance.getLangMessage("tpa-command-expire-from-message") + p.getName()
                                + " expired.");
            }
        };
        req.setTask(task);
        task.runTaskLater(RolleritePlugin.instance, 20 * 10);
        pendingRequests.add(req);

        p.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-send-message"));
        target.sendMessage(RolleritePlugin.instance.getLangMessage("tpa-command-incoming-message") + p.getName() + ". "
                + "Please use /tpa accept or /tpa deny.");
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

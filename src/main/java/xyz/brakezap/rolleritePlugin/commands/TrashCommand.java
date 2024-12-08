package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import xyz.brakezap.rolleritePlugin.RolleritePlugin;
import xyz.brakezap.rolleritePlugin.guis.TrashInventory;

public class TrashCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender()
                    .sendMessage(RolleritePlugin.instance.getLangMessage("trash-command-console-message"));
            return;
        }

        Player p = (Player) commandSourceStack.getSender();
        p.openInventory(new TrashInventory(p).getInventory());
    }

    @Override
    public @Nullable String permission() {
        return "RP.trash";
    }

}

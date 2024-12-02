package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import xyz.brakezap.rolleritePlugin.guis.TrashInventory;

import static net.kyori.adventure.text.Component.text;


public class TrashCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage("Cannot execute the trash command as console!");
            return;
        }

        Player p = (Player) commandSourceStack.getSender();
        //TODO: Locale here
        p.sendMessage(text("Opening trash...").color(NamedTextColor.RED));
        p.openInventory(new TrashInventory(p).getInventory());
    }


    @Override
    public @Nullable String permission() {
        return "RP.trash";
    }

}

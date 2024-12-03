package xyz.brakezap.rolleritePlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public class FixCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            commandSourceStack.getSender().sendMessage("Cannot execute the trash command as console!");
            return;
        }
        Player p = (Player) commandSourceStack.getSender();

        if (p.getInventory().getItem(EquipmentSlot.HAND).getType().equals(Material.AIR)) {
            //TODO: Locale
            p.sendMessage("You must be holding an item to fix it!");
            return;
        }

        ItemStack stack = p.getInventory().getItem(EquipmentSlot.HAND);
        Damageable damageable = (Damageable) stack.getItemMeta();
        damageable.resetDamage();

        stack.setItemMeta(damageable);

        //TODO: Locale
        p.sendMessage("Fixed your item!");
    }

    @Override
    public @Nullable String permission() {
        return "RP.fix";
    }
}

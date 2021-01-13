package fr.linedev.infinityliquid.listeners;

import fr.linedev.infinityliquid.managers.Managers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayersEvent implements Listener {

    private final Managers managers = Managers.getManagers();

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {

        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (item.getType() == Material.WATER_BUCKET) {
                if (!managers.perms.has(event.getPlayer(), "infliquid.use.water")) {
                    if (!managers.permsMessage.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.permsMessage.replaceAll("%permission%", "infliquid.use.water")));
                    }
                    event.setCancelled(true);
                    return;
                }

                if (managers.waterPrice <= 0.0D) {
                    event.getBlock().setType(Material.WATER);
                    event.setCancelled(true);
                    return;
                }

                if (!managers.econ.has(managers.getOfflinePlayer(p), managers.waterPrice)) {
                    if (!managers.notEnoughMoney.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.notEnoughMoney));
                    }

                    event.setCancelled(true);
                    return;
                }

                managers.econ.withdrawPlayer(managers.getOfflinePlayer(p), managers.waterPrice);

                if (!managers.subractMoney.isEmpty()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.subractMoney.replaceAll("%cost%", managers.waterPrice + "")));
                }

                event.getBlock().setType(Material.WATER);
                event.setCancelled(true);
                return;
            }

            if (item.getType() == Material.LAVA_BUCKET) {
                if (!managers.perms.has(event.getPlayer(), "infliquid.use.lava")) {
                    if (!managers.permsMessage.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.permsMessage.replaceAll("%permission%", "infliquid.use.lava")));
                    }
                    event.setCancelled(true);
                    return;
                }

                if (managers.lavaPrice <= 0.0D) {
                    event.getBlock().setType(Material.LAVA);
                    event.setCancelled(true);
                    return;
                }

                if (!managers.econ.has(managers.getOfflinePlayer(p), managers.lavaPrice)) {
                    if (!managers.notEnoughMoney.isEmpty()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.notEnoughMoney));
                    }

                    event.setCancelled(true);
                    return;
                }

                managers.econ.withdrawPlayer(managers.getOfflinePlayer(p), managers.lavaPrice);

                if (!managers.subractMoney.isEmpty()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', managers.subractMoney.replaceAll("%cost%", managers.lavaPrice + "")));
                }

                event.getBlock().setType(Material.LAVA);
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (item != null) {
            if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
                if (item.getType() == Material.LAVA_BUCKET) {
                    if (!item.getItemMeta().equals(managers.getLavaBucket().getItemMeta())) {
                        item.setItemMeta(managers.getLavaBucket().getItemMeta());
                    }

                    return;
                }

                if (item.getType() == Material.WATER_BUCKET && !item.getItemMeta().equals(managers.getWaterBucket().getItemMeta())) {
                    item.setItemMeta(managers.getWaterBucket().getItemMeta());
                }
            }

        }
    }
}

package fr.linedev.infinityliquid.commands;

import fr.linedev.infinityliquid.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandInfLiquid implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            if (!Managers.getManagers().perms.has(sender, "infliquid.help")) {
                return true;
            } else {
                Managers.getManagers().displayHelpMessage(sender);
                return true;
            }
        } else {
            String arg1 = args[0];
            if (arg1.equalsIgnoreCase("reload")) {
                if (!Managers.getManagers().perms.has(sender, "infliquid.reload")) {
                    if (!Managers.getManagers().permsMessage.isEmpty()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Managers.getManagers().permsMessage.replaceAll("%permission%", "infliquid.reload")));
                    }

                    return true;
                } else {
                    Managers.getManagers().reload();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                    return true;
                }
            } else if (arg1.equalsIgnoreCase("help")) {
                if (!Managers.getManagers().perms.has(sender, "infliquid.help")) {
                    if (!Managers.getManagers().permsMessage.isEmpty()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Managers.getManagers().permsMessage.replaceAll("%permission%", "infliquid.help")));
                    }

                    return true;
                } else {
                    Managers.getManagers().displayHelpMessage(sender);
                    return true;
                }
            } else {
                if (arg1.equalsIgnoreCase("give")) {
                    if (!Managers.getManagers().perms.has(sender, "infliquid.give")) {
                        if (!Managers.getManagers().permsMessage.isEmpty()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Managers.getManagers().permsMessage.replaceAll("%permission%", "infliquid.give")));
                        }

                        return true;
                    }

                    if (args.length < 2) {
                        sender.sendMessage(ChatColor.RED + "Please insert player name");
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "That player isn't online");
                        return true;
                    }

                    if (args.length < 3) {
                        sender.sendMessage(ChatColor.RED + "Please insert bucket type: lava or water");
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("lava")) {
                        target.getInventory().addItem(new ItemStack[]{Managers.getManagers().getLavaBucket()});
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("water")) {
                        target.getInventory().addItem(new ItemStack[]{Managers.getManagers().getWaterBucket()});
                        return true;
                    }
                }

                sender.sendMessage(ChatColor.RED + "Unknown arguments. Please try " + ChatColor.WHITE + "/infliquid help");
                return true;
            }
        }
    }
}

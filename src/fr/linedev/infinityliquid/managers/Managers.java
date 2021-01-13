package fr.linedev.infinityliquid.managers;

import fr.linedev.infinityliquid.InfinityLiquidMain;
import fr.linedev.infinityliquid.tools.ConfigBuilder;
import fr.linedev.infinityliquid.tools.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Managers {

    public boolean enchant;
    private String lavaDisplayName;
    private String waterDisplayName;
    public String permsMessage;
    public String notEnoughMoney;
    public String subractMoney;
    public List waterLore;
    public List lavaLore;
    public Integer waterPrice;
    public Integer lavaPrice;
    public Economy econ;
    public Permission perms;


    private static InfinityLiquidMain instance;
    private static Managers managers;

    public void load(InfinityLiquidMain instance) {
        Managers.instance = instance;
        Managers.managers = this;

        if (!this.setupEconomy()) {
            instance.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", instance.getDescription().getName()));
            instance.getServer().getPluginManager().disablePlugin(instance);
        } else {
            this.setupPermissions();
            instance.saveDefaultConfig();
            this.configAll();

            Bukkit.getConsoleSender().sendMessage("Le plugin InfinityLiquid Active");

            CommandsManager.register(instance);
            EventsManager.register(instance);
        }
    }

    public void unload() {
        Bukkit.getConsoleSender().sendMessage("Le plugin InfinityLiquid désactiver");
    }

    public static InfinityLiquidMain getInstance() {
        return instance;
    }

    public static Managers getManagers() {
        return managers;
    }

    public OfflinePlayer getOfflinePlayer(Player p) {
        return Bukkit.getOfflinePlayer(p.getUniqueId());
    }

    private boolean setupEconomy() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = instance.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                this.econ = (Economy) rsp.getProvider();
                return this.econ != null;
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = instance.getServer().getServicesManager().getRegistration(Permission.class);
        this.perms = (Permission) rsp.getProvider();
        return this.perms != null;
    }

    public void displayHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "------------" + ChatColor.GOLD + "Infinity Liquid" + ChatColor.AQUA + "------------");
        sender.sendMessage(ChatColor.WHITE + "/infLiqud help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Shows this message");
        sender.sendMessage(ChatColor.WHITE + "/infLiqud reload" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Reloads the configuration");
        sender.sendMessage(ChatColor.WHITE + "/infLiqud give <playername> <water|lava>" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Gives said player a infinite liquid");
        sender.sendMessage(ChatColor.AQUA + "-------------------------------------");
    }


    public void reload() {
        ConfigBuilder.reload();
        ConfigBuilder.saveDefaultConfig();
        ConfigBuilder.copyDefaults();
        ConfigBuilder.saveConfig();
        ConfigBuilder.reload();
        this.configAll();
    }

    public void configAll() {
        //Config globals
        this.enchant = ConfigBuilder.getBoolean("globals.enchant");
        this.permsMessage = ConfigBuilder.getString("globals.notEnoughPermissions");
        this.notEnoughMoney = ConfigBuilder.getString("globals.notEnoughMoney");
        this.subractMoney = ConfigBuilder.getString("globals.subtractMoney");

        //Config water
        this.waterDisplayName = ConfigBuilder.getString("water.displayName");
        this.waterLore = ConfigBuilder.getList("water.lore");
        this.waterPrice = ConfigBuilder.getInt("water.price");

        //Config lava
        this.lavaDisplayName = ConfigBuilder.getString("lava.displayName");
        this.lavaLore = ConfigBuilder.getList("lava.lore");
        this.lavaPrice = ConfigBuilder.getInt("lava.price");
    }

    //    Create item water bucket
    public ItemStack getWaterBucket() {
        ItemBuilder stack = new ItemBuilder(Material.WATER_BUCKET)
                .setName(this.waterDisplayName)
                .setCustomModelData(1)
                .addFlag(ItemFlag.HIDE_ENCHANTS);

        if (!this.waterLore.isEmpty()) {

            ArrayList lore = new ArrayList();
            Iterator loreIterator = this.waterLore.iterator();
            while (loreIterator.hasNext()) {
                String text = (String) loreIterator.next();
                lore.add(text.replace("%cost%", this.waterPrice + "").replace("&", "§"));
            }
            stack.setLore(lore);
        }

        if (this.enchant) {
            stack.addEnchant(Enchantment.DURABILITY, 1);
        }

        return stack.toItemStack();
    }


    //    Create item lava bucket
    public ItemStack getLavaBucket() {
        ItemBuilder stack = new ItemBuilder(Material.LAVA_BUCKET)
                .setName(this.lavaDisplayName)
                .setCustomModelData(1)
                .addFlag(ItemFlag.HIDE_ENCHANTS);

        if (!this.lavaLore.isEmpty()) {

            ArrayList lore = new ArrayList();
            Iterator loreIterator = this.lavaLore.iterator();
            while (loreIterator.hasNext()) {
                String text = (String) loreIterator.next();
                lore.add(text.replace("%cost%", this.lavaPrice + "").replace("&", "§"));
            }
            stack.setLore(lore);
        }

        if (this.enchant) {
            stack.addEnchant(Enchantment.DURABILITY, 1);
        }

        return stack.toItemStack();
    }
}

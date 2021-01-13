package fr.linedev.infinityliquid.managers;

import fr.linedev.infinityliquid.InfinityLiquidMain;
import fr.linedev.infinityliquid.commands.CommandInfLiquid;

public class CommandsManager {
    public static void register(InfinityLiquidMain instance) {
        instance.getCommand("infliquid").setExecutor(new CommandInfLiquid());
    }
}

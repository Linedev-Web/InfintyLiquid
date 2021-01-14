package fr.linedev.infinityliquid;

import fr.linedev.infinityliquid.managers.Managers;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinityLiquidMain extends JavaPlugin {

    Managers managers = new Managers();

    @Override
    public void onEnable() {
        managers.load(this);
    }

    @Override
    public void onDisable() {
        managers.unload();
    }

}

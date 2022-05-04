package me.godofpro.hardcorelives;

import co.aikar.commands.PaperCommandManager;
import me.godofpro.hardcorelives.commands.LiveCommand;
import me.godofpro.hardcorelives.commands.MainCommand;
import me.godofpro.hardcorelives.listeners.PlayerDeathListener;
import me.godofpro.hardcorelives.listeners.PlayerJoinListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hardcorelives extends JavaPlugin {
    private static Economy econ = null;

    @Override
    public void onEnable() {
        if(!setupEconomy())
        {
            getServer().getLogger().warning("Disaling the buy command because no vault");
            // TODO make the buy command
            // TODO disabled the buy command if there is no economy
        }
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        PaperCommandManager pcm = new PaperCommandManager(this);
        pcm.registerCommand(new LiveCommand(this));
        pcm.registerCommand(new MainCommand(this));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}

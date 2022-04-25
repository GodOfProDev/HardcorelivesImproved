package me.godofpro.hardcorelives;

import co.aikar.commands.PaperCommandManager;
import me.godofpro.hardcorelives.commands.LiveCommand;
import me.godofpro.hardcorelives.commands.MainCommand;
import me.godofpro.hardcorelives.listeners.PlayerDeathListener;
import me.godofpro.hardcorelives.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hardcorelives extends JavaPlugin {

    @Override
    public void onEnable() {
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
}

package me.godofpro.hardcorelives.listeners;

import me.godofpro.hardcorelives.Hardcorelives;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    private final Hardcorelives plugin;
    public PlayerJoinListener(Hardcorelives plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        NamespacedKey killKey = new NamespacedKey(plugin, "kill-count");
        NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(liveKey, PersistentDataType.INTEGER)) {
            data.set(liveKey, PersistentDataType.INTEGER, 3);
        }
        if(!data.has(killKey, PersistentDataType.INTEGER)) {
            data.set(killKey, PersistentDataType.INTEGER, 0);
        }
        if(!data.has(maxLiveKey, PersistentDataType.INTEGER)) {
            data.set(maxLiveKey, PersistentDataType.INTEGER, 3);
        }
    }
}

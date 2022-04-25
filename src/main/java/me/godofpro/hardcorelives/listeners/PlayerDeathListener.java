package me.godofpro.hardcorelives.listeners;

import me.godofpro.hardcorelives.Hardcorelives;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeathListener implements Listener {

    private final Hardcorelives plugin;
    public PlayerDeathListener(Hardcorelives plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.hasPermission("HardcoreLives.invincibility")) return;

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        int liveCount = data.get(liveKey, PersistentDataType.INTEGER);

        liveCount--;

        if(liveCount <= 0) {
            // TODO add config stuff for this bullshit
            data.set(liveKey, PersistentDataType.INTEGER, 1);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tempban " + player.getName() + " 1d deathbanned!");
            return;
        }
        data.set(liveKey, PersistentDataType.INTEGER, liveCount);
        player.sendMessage(ChatColor.RED + "You Died! You now have " + liveCount + " lives");
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        NamespacedKey killKey = new NamespacedKey(plugin, "kill-count");
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");

        if(killer == null || killer.getUniqueId() == player.getUniqueId()) return;
        PersistentDataContainer data = killer.getPersistentDataContainer();

        int killCount = data.get(killKey, PersistentDataType.INTEGER);
        killCount++;

        if(killCount >= 2) {
            int liveCount = data.get(liveKey, PersistentDataType.INTEGER);
            liveCount++;
            int maxLiveCount = data.get(maxLiveKey, PersistentDataType.INTEGER);
            if(liveCount > maxLiveCount) {
                killer.sendMessage(ChatColor.RED + "You have reached the max lives!");
                return;
            }
            data.set(liveKey, PersistentDataType.INTEGER, liveCount);
            data.set(killKey, PersistentDataType.INTEGER, 0);
            killer.sendMessage(ChatColor.GREEN + "You have killed " + player.getName() + "! You now have " + liveCount + " lives");
            return;
        }
        data.set(killKey, PersistentDataType.INTEGER, killCount);
        killer.sendMessage(ChatColor.RED + "You killed a player! You now have " + killCount + " kills");
    }
}

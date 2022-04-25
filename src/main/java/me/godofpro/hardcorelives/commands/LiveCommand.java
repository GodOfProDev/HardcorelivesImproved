package me.godofpro.hardcorelives.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.godofpro.hardcorelives.Hardcorelives;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@CommandAlias("live")
public class LiveCommand extends BaseCommand {
    private final Hardcorelives plugin;
    public LiveCommand(Hardcorelives plugin)
    {
        this.plugin = plugin;
    }

    @Subcommand("set")
    @Description("Set the player lives")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-100")
    public void onSetCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        int amount = 0;
        try {
            player = Bukkit.getPlayer(args[0]);
            amount = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live set <player> <amount>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");
        int maxAmount = data.get(maxLiveKey, PersistentDataType.INTEGER);
        if(amount > maxAmount) {
            player.sendMessage(ChatColor.RED + "You can't set more lives than the max lives");
            return;
        }
        data.set(liveKey, PersistentDataType.INTEGER, amount);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " lives has been set to " + amount);
    }

    @Subcommand("add")
    @Description("Add lives to a player")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-100")
    public void onAddCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        int amount = 0;
        try {
            player = Bukkit.getPlayer(args[0]);
            amount = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live add <player> <amount>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");
        int currentAmount = data.get(liveKey, PersistentDataType.INTEGER);
        int maxAmount = data.get(maxLiveKey, PersistentDataType.INTEGER);
        if (currentAmount + amount > maxAmount) {
            player.sendMessage(ChatColor.RED + "You can't add more lives than the max lives");
            return;
        }
        data.set(liveKey, PersistentDataType.INTEGER, currentAmount + amount);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " lives has been added to " + amount);
    }

    @Subcommand("remove")
    @Description("Remove lives from a player")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-100")
    public void onRemoveCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        int amount = 0;
        try {
            player = Bukkit.getPlayer(args[0]);
            amount = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live remove <player> <amount>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        int currentAmount = data.get(liveKey, PersistentDataType.INTEGER);
        data.set(liveKey, PersistentDataType.INTEGER, currentAmount - amount);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " lives has been removed to " + amount);
    }

    @Subcommand("get")
    @Description("Get the lives of a player")
    @Syntax("<player>")
    @CommandCompletion("@players")
    public void onGetCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        try {
            player = Bukkit.getPlayer(args[0]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live get <player>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        int currentAmount = data.get(liveKey, PersistentDataType.INTEGER);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " lives is " + currentAmount);
    }

    @Subcommand("reset")
    @Description("Reset the lives of a player to 1")
    @Syntax("<player>")
    @CommandCompletion("@players")
    public void onResetCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        try {
            player = Bukkit.getPlayer(args[0]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live reset <player>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        data.set(liveKey, PersistentDataType.INTEGER, 1);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " lives has been reset");
    }

    @Subcommand("resetall")
    @Description("Reset the lives of all players to 1")
    public void onResetAllCommand(CommandSender sender, String[] args) {
        // Args
        for (Player player : Bukkit.getOnlinePlayers()) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
            data.set(liveKey, PersistentDataType.INTEGER, 1);
        }
        sender.sendMessage(ChatColor.GREEN + "All players lives has been reset");
    }

    @Subcommand("setmax")
    @Description("Set the max lives of a player")
    @Syntax("<player> <amount>")
    @CommandCompletion("@players @range:1-100")
    public void onSetMaxCommand(CommandSender sender, String[] args) {
        // Args
        Player player = null;
        int amount = 0;
        try {
            player = Bukkit.getPlayer(args[0]);
            amount = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live setmax <player> <amount>");
            return;
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live setmax <player> <amount>");
            return;
        }

        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");
        data.set(maxLiveKey, PersistentDataType.INTEGER, amount);
        sender.sendMessage(player.getDisplayName() + ChatColor.GREEN + " max lives has been set to " + amount);
    }

    @Subcommand("setmaxall")
    @Description("Set the max lives of all players")
    @Syntax("<amount>")
    @CommandCompletion("@range:1-100")
    public void onSetMaxAllCommand(CommandSender sender, String[] args) {
        // Args
        int amount = 0;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /live setmaxall <amount>");
            return;
        }

        if(amount <= 0) {
            sender.sendMessage(ChatColor.RED + " The amount must be greater than 0");
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            NamespacedKey maxLiveKey = new NamespacedKey(plugin, "live-max");
            data.set(maxLiveKey, PersistentDataType.INTEGER, amount);
        }
        sender.sendMessage(ChatColor.GREEN + "All players max lives has been set to " + amount);
    }

    @Default
    public void onLiveCommand(CommandSender sender)
    {
        if (! (sender instanceof Player)) return;
        Player player = (Player) sender;
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey liveKey = new NamespacedKey(plugin, "live-count");
        int liveCount = data.get(liveKey, PersistentDataType.INTEGER);
        player.sendMessage(ChatColor.RED + "You have " + liveCount + " lives");
    }
}

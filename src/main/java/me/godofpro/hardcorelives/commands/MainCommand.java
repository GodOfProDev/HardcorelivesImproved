package me.godofpro.hardcorelives.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import me.godofpro.hardcorelives.Hardcorelives;
import org.bukkit.command.CommandSender;

@CommandAlias("hardcorelives")
public class MainCommand extends BaseCommand {
    private final Hardcorelives plugin;
    public MainCommand(Hardcorelives plugin)
    {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @Description("Reloads the config")
    public void reload(CommandSender sender)
    {
        plugin.reloadConfig();
        sender.sendMessage("§a§lHardcoreLives §f§lReloaded");
    }

    @Subcommand("help")
    @Description("Shows the help")
    public void help(CommandSender sender)
    {
        sender.sendMessage("§a§lHardcoreLives §f§lHelp");
        sender.sendMessage("§a/hardcorelives reload §f- Reloads the config");
        sender.sendMessage("§a/hardcorelives help §f- Shows this help");
        sender.sendMessage("§a/live §f- Shows how many lives you got");
        sender.sendMessage("§a/live add <player> <amount> §f- Adds a certain amount of lives from the player");
        sender.sendMessage("§a/live remove <player> <amount> §f- Removes a certain amount of lives from the player");
        sender.sendMessage("§a/live set <player> <amount> §f- Sets a certain amount of lives from the player");
        sender.sendMessage("§a/live get <player> <amount> §f- Get how many lives the player has");
        sender.sendMessage("§a/live give <player> <amount> §f- Give a certain amount of lives to the player");
        sender.sendMessage("§a/live take <player> <amount> §f- Take a certain amount of lives from the player");
        sender.sendMessage("§a/live reset <player> §f- Resets the player's lives");
        sender.sendMessage("§a/live resetall §f- Resets all players' lives");
        sender.sendMessage("§a/live setmax <player> <amount> §f- Sets the player's max lives");
        sender.sendMessage("§a/live setmaxall <amount> §f- Sets all players' max lives");

    }
}

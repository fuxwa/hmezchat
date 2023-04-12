package net.heavenmine.hmeasychat.command;

import net.heavenmine.hmeasychat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnMuteCommand implements CommandExecutor {
    private final Main main;

    public UnMuteCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unmute <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (target.hasMetadata("muted")) {
            target.removeMetadata("muted", main);
            sender.sendMessage(ChatColor.GREEN + "Player unmuted.");
        } else {
            sender.sendMessage(ChatColor.RED + "Player is not muted.");
        }
        return true;
    }
}

package net.heavenmine.hmeasychat.command;

import net.heavenmine.hmeasychat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class MuteCommand implements CommandExecutor {
    private final Main main;

    public MuteCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /mute <player> [duration]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        long duration = 0;
        if (args.length == 2) {
            try {
                duration = Long.parseLong(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid duration.");
                return true;
            }
        }

        target.setMetadata("muted", new FixedMetadataValue(main, true));
        if (duration > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    if (target.hasMetadata("muted")) {
                        target.removeMetadata("muted", main);
                        target.sendMessage(ChatColor.GREEN + "You are no longer muted.");
                    }
                }
            }, duration * 20);
            sender.sendMessage(ChatColor.GREEN + "Player muted for " + duration + " seconds.");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Player muted.");
        }
        return true;
    }
}

package net.heavenmine.hmeasychat.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CLearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        for (int i = 0; i < 120; i++) {
            player.sendMessage(" ");
        }

        player.sendMessage(ChatColor.YELLOW + "Your chat has been cleared.");
        return true;
    }
}

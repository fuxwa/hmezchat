package net.heavenmine.hmeasychat.command;

import net.heavenmine.hmeasychat.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickNameCommand implements CommandExecutor {
    private final Main main;
    public NickNameCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /nickname <nickname>");
            return true;
        }

        String nickname = ChatColor.translateAlternateColorCodes('&', args[0]);
        player.setDisplayName(nickname);
        player.sendMessage(ChatColor.GREEN + "Your nickname has been changed to " + nickname + ".");

        // Lưu nickname vào file config hoặc database
//        main.getConfig().set("nickname." + player.getUniqueId().toString(), nickname);
//        main.saveConfig();

        return true;
    }
}

package net.heavenmine.hmeasychat.command;

import net.heavenmine.hmeasychat.Main;
import net.heavenmine.hmeasychat.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class ChannelCommand implements CommandExecutor {
    private final Main main;
    public ChannelCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigFile configFile = new ConfigFile(main);
        String prefix = configFile.getPrefix();
        if (!(sender instanceof Player)) {
            sender.sendMessage("Lệnh này chỉ có thể được sử dụng bởi người chơi!");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Use: /channel <channel-name>");
            return true;
        }
        List<String> channels = configFile.getChannel();
        if(channels.contains(args[0])) {
            String per = main.getConfig().getString("channel-format." + args[0] + ".permission");
            if(player.hasPermission(per)) {
                player.setMetadata("chat", new FixedMetadataValue(main, args[0]));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aĐã đổi sang kênh &6" + args[0] + " thành công"));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cBạn không có quyền để chat ở kênh này"));
            }

        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cKhông tìm thấy kênh này trong file config"));
        }
        return false;
    }
}

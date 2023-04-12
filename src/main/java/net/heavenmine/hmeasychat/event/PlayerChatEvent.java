package net.heavenmine.hmeasychat.event;

import net.heavenmine.hmeasychat.Main;
import net.heavenmine.hmeasychat.PermissionManager;
import net.heavenmine.hmeasychat.file.ConfigFile;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerChatEvent implements Listener {
    private Main main;
    public PlayerChatEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        event.setCancelled(true);
        Player player = event.getPlayer();
        if (player.hasMetadata("muted") && player.getMetadata("muted").get(0).asBoolean()) {
//            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are muted.");
        } else {
            PermissionManager permissionManager = new PermissionManager();
            String prefix = permissionManager.getPlayerPrefix(player);
            String suffix = permissionManager.getPlayerSurffix(player);
            String channel = getChannel(player);
            String channel_tag = ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("channel-format." + channel + ".prefix"));
            String messageColour = main.getConfig().getString("channel-format." + channel + ".messageColour");
            String message;
            if (player.hasPermission("hmezchat.chat.color")) {
                message = channel_tag + ChatColor.translateAlternateColorCodes('&', prefix + "&f" + player.getDisplayName() + suffix + ": " + messageColour + event.getMessage() );
            } else {
                message = channel_tag + ChatColor.translateAlternateColorCodes('&', prefix + "&f" + player.getDisplayName() + suffix + ": " + messageColour ) + event.getMessage();
            }
            Logger logger = Logger.getLogger("Minecraft");
            logger.info(message);
            for (Player recipient : event.getRecipients()) {
                recipient.sendMessage(message);
            }
            event.getRecipients().clear();
            event.getRecipients().addAll(getRecipients(channel));
        }

    }
    // Lấy kênh chat của người chơi từ metadata
    private String getChannel(Player player) {
        if (player.hasMetadata("chat")) {
            MetadataValue metadata = player.getMetadata("chat").get(0);
            if (metadata != null && metadata.value() instanceof String) {
                return (String) metadata.value();
            }
        }
        return "global"; // Kênh chat mặc định
    }
    // Lấy danh sách người chơi nhận tin nhắn trong kênh chat hiện tại
    private List<Player> getRecipients(String channel) {
        List<Player> recipients = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasMetadata("channel")) {
                MetadataValue metadata = player.getMetadata("channel").get(0);
                if (metadata != null && metadata.value() instanceof String && ((String) metadata.value()).equals(channel)) {
                    recipients.add(player);
                }
            }
        }
        return recipients;
    }
}

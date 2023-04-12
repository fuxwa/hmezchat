package net.heavenmine.hmeasychat;

import net.heavenmine.hmeasychat.command.*;
import net.heavenmine.hmeasychat.event.PlayerChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        checkLuckPerm();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);

        getCommand("channel").setExecutor(new ChannelCommand(this));
        getCommand("mute").setExecutor(new MuteCommand(this));
        getCommand("unmute").setExecutor(new UnMuteCommand(this));
        getCommand("nickname").setExecutor(new NickNameCommand(this));
        getCommand("clear").setExecutor(new CLearChatCommand());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Đặt kênh chat mặc định cho người chơi khi vào game
        Player player = event.getPlayer();
        player.setMetadata("channel", new FixedMetadataValue(this, "global"));
    }
    @Override
    public void onDisable() {
    }
    public void checkLuckPerm() {
        Plugin luckPerms = getServer().getPluginManager().getPlugin("LuckPerms");
        if (luckPerms == null){
            getLogger().severe("*** LuckPerms is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
        }
    }
}

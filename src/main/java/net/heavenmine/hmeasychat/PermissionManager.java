package net.heavenmine.hmeasychat;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;


public class PermissionManager {
    public String getPlayerPrefix(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        return user.getCachedData().getMetaData().getPrefix();
    }
    public String getPlayerSurffix(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        return user.getCachedData().getMetaData().getSuffix();
    }
}

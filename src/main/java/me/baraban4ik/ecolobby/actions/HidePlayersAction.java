package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HidePlayersAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()) {
            player.hidePlayer(EcoLobby.getInstance(), otherPlayer);
        }
    }
}
package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShowPlayersAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Bukkit.getServer().getOnlinePlayers().forEach(
                otherPlayer -> player.showPlayer(EcoLobby.getInstance(), otherPlayer)
        );
    }
}
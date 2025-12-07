package me.baraban4ik.ecolobby.actions;


import me.baraban4ik.ecolobby.EcoLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HidePlayersAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        Bukkit.getOnlinePlayers().forEach(
                otherPlayer -> player.hidePlayer(EcoLobby.getInstance(), otherPlayer)
        );
    }
}
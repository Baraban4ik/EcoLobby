package me.baraban4ik.ecolobby.actions;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.models.PlayerAction;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ConnectAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        try (ByteArrayOutputStream data = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(data)) {

            out.writeUTF("Connect");
            out.writeUTF(action);

            player.sendPluginMessage(EcoLobby.getInstance(), "BungeeCord", data.toByteArray());
        } catch (Exception ignored) {}
    }
}
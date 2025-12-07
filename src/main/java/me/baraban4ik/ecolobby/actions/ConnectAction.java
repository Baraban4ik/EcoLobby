package me.baraban4ik.ecolobby.actions;


import me.baraban4ik.ecolobby.EcoLobby;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectAction implements PlayerAction {
    @Override
    public void execute(Player player, String action) {
        try (ByteArrayOutputStream data = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(data)) {

            out.writeUTF("Connect");
            out.writeUTF(action);

            player.sendPluginMessage(EcoLobby.getInstance(), "BungeeCord", data.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
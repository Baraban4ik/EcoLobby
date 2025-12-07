package me.baraban4ik.ecolobby.managers.display;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.managers.PlayerAware;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractDisplayManager<T> implements PlayerAware {

    protected final Map<UUID, T> playerDisplays = new HashMap<>();
    private BukkitTask updateTask;

    public abstract boolean isEnabled();

    protected abstract long getRefreshTicks();
    protected abstract void createDisplay(Player player);

    protected void removeDisplay(T display) {}
    protected void removeDisplay(Player player) {}

    @Override
    public void start() {
        if (updateTask != null) return;

        updateTask = Bukkit.getScheduler().runTaskTimer(EcoLobby.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                update(player);
            }
        }, 0L, getRefreshTicks());
    }

    @Override
    public void stop() {
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }
        removeAllPlayers();
    }

    public void send(Player player) {
        if (playerDisplays.containsKey(player.getUniqueId()) || updateTask == null) return;
        createDisplay(player);
        update(player);
    }

    public void removeAllPlayers() {
        playerDisplays.forEach(((uuid, display) -> {
            if (display != null) removeDisplay(display);
            removeDisplay(Bukkit.getPlayer(uuid));
        }));
        playerDisplays.clear();
    }

    protected void addPlayer(Player player, T value) {
        playerDisplays.put(player.getUniqueId(), value);
    }

    @Override
    public void removePlayer(Player player) {
        playerDisplays.remove(player.getUniqueId());
    }
}

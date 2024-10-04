package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static me.baraban4ik.ecolobby.EcoLobby.bossBarConfig;

public class BossBarManager {

    private final Map<Player, BossBar> playerBossBars = new HashMap<>();

    public void sendBossBar(Player player) {
        if (!playerBossBars.containsKey(player)) {
            String title = Format.format(bossBarConfig.getString(Path.BOSSBAR_TITLE.getPath()), player);

            BarColor color = BarColor.valueOf(bossBarConfig.getString(Path.BOSSBAR_COLOR.getPath(), "GREEN"));
            BarStyle style = BarStyle.valueOf(bossBarConfig.getString(Path.BOSSBAR_STYLE.getPath(), "SOLID"));

            BossBar bossBar = Bukkit.createBossBar(title, color, style);
            bossBar.addPlayer(player);

            playerBossBars.put(player, bossBar);

            Bukkit.getScheduler().runTaskTimerAsynchronously(EcoLobby.getInstance(), () -> update(player), 0L, bossBarConfig.getInt(Path.BOSSBAR_REFRESH.getPath()) * 20L);
        }
        else {
            BossBar bossBar = playerBossBars.get(player);
            bossBar.addPlayer(player);
        }
    }

    public void update(Player player) {
        BossBar bossBar = playerBossBars.get(player);
        if (bossBar != null) {
            String title = Format.format(bossBarConfig.getString(Path.BOSSBAR_TITLE.getPath()), player);

            BarColor color = BarColor.valueOf(bossBarConfig.getString(Path.BOSSBAR_COLOR.getPath(), "GREEN"));
            BarStyle style = BarStyle.valueOf(bossBarConfig.getString(Path.BOSSBAR_STYLE.getPath(), "SOLID"));

            double progress = Math.max(0, Math.min(bossBarConfig.getDouble(Path.BOSSBAR_PROGRESS.getPath()) / 100, 1));

            bossBar.setTitle(title);
            bossBar.setColor(color);

            bossBar.setStyle(style);
            bossBar.setProgress(progress);
        }
    }

    public void removeAllPlayers() {
        for (BossBar bossBar : playerBossBars.values()) {
            bossBar.removeAll();
        }
        playerBossBars.clear();
    }
}

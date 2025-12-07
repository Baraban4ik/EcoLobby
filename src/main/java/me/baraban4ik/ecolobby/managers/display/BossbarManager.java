package me.baraban4ik.ecolobby.managers.display;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.BossbarConfig;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossbarManager extends AbstractDisplayManager<BossBar>{

    private final BossbarConfig bossbarConfig = ConfigManager.getBossbarConfig();

    @Override
    public boolean isEnabled() {
        return bossbarConfig.isEnabled();
    }

    @Override
    protected long getRefreshTicks() {
        return bossbarConfig.getRefresh() * 20L;
    }

    @Override
    protected void createDisplay(Player player) {
        String title = bossbarConfig.getTitle().toString(player);
        BarColor color = bossbarConfig.getColor();
        BarStyle style = bossbarConfig.getStyle();

        BossBar bossBar = Bukkit.createBossBar(title, color, style);
        bossBar.addPlayer(player);

        addPlayer(player, bossBar);
    }

    @Override
    public void update(Player player) {
        BossBar bossBar = playerDisplays.get(player.getUniqueId());
        if (bossBar == null) return;

        bossBar.setTitle(bossbarConfig.getTitle().toString(player));
        bossBar.setColor(bossbarConfig.getColor());
        bossBar.setStyle(bossbarConfig.getStyle());
        bossBar.setProgress(bossbarConfig.getProgress());
    }

    @Override
    protected void removeDisplay(BossBar display) {
        display.removeAll();
    }
}

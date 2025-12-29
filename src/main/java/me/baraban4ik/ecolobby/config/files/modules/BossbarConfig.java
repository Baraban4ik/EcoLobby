package me.baraban4ik.ecolobby.config.files.modules;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.BossbarPath;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import me.baraban4ik.ecolobby.utils.EnumUtils;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

@Getter
public class BossbarConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private double progress;
    private FormattedMessage title;
    private BarColor color;
    private BarStyle style;

    @Override
    protected void loadValues() {
        enabled = getBoolean(BossbarPath.ENABLED);
        refresh = getInt(BossbarPath.REFRESH);
        progress = getNormalizedProgress();
        title = getFormattedMessage(BossbarPath.TITLE);
        color = EnumUtils.parseEnum(
                getString(BossbarPath.COLOR),
                BarColor.class, BarColor.GREEN
        );
        style = EnumUtils.parseEnum(
                getString(BossbarPath.STYLE),
                BarStyle.class, BarStyle.SOLID
        );
    }

    private double getNormalizedProgress() {
        int rawProgress = getInt(BossbarPath.PROGRESS, 100);
        double normalizedProgress = rawProgress / 100d;

        return Math.max(0, Math.min(normalizedProgress, 1));
    }

    @Override
    public String getPath() {
        return FilePath.BOSSBAR.get();
    }
}

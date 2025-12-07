package me.baraban4ik.ecolobby.config.files;


import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.enums.paths.BossbarPath;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import me.baraban4ik.ecolobby.utils.EnumUtils;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
@Getter
public class BossbarConfig extends AbstractConfig {

    private boolean enabled;
    private long refresh;
    private double progress;
    private FormattedMessage title;
    private BarColor color;
    private BarStyle style;

    @Override
    public void initialize(ConfigurationSection config) {
        super.initialize(config);

        enabled = getBoolean(BossbarPath.ENABLED);
        refresh = getInt(BossbarPath.REFRESH);
        progress = Math.max(0, Math.min(getInt(BossbarPath.PROGRESS, 100) / 100d, 1));
        title = getFormattedMessage(BossbarPath.TITLE);
        color = EnumUtils.parseEnum(getString(BossbarPath.COLOR), BarColor.class, BarColor.GREEN);
        style = EnumUtils.parseEnum(getString(BossbarPath.STYLE), BarStyle.class, BarStyle.SOLID);
    }

    @Override
    public String getPath() {
        return FilePath.BOSSBAR.get();
    }
}

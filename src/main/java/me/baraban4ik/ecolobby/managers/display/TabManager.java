package me.baraban4ik.ecolobby.managers.display;

import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.TablistConfig;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.entity.Player;

public class TabManager extends AbstractDisplayManager<Void> {

    private final TablistConfig tablistConfig;

    public TabManager() {
        tablistConfig = ConfigManager.getTablistConfig();
    }

    @Override
    public boolean isEnabled() {
        return tablistConfig.isEnabled();
    }

    @Override
    protected long getRefreshTicks() {
        return tablistConfig.getRefresh() * 20L;
    }

    @Override
    protected void createDisplay(Player player) {
        addPlayer(player, null);
    }

    @Override
    public void update(Player player) {
        FormattedMessage header = tablistConfig.getHeader();
        FormattedMessage footer = tablistConfig.getFooter();

        FormattedMessage prefix = tablistConfig.getPrefix();
        FormattedMessage suffix = tablistConfig.getSuffix();

        player.setPlayerListName(prefix.toString(player) + player.getName() + suffix.toString(player));
        player.setPlayerListHeader(header.toString(player));
        player.setPlayerListFooter(footer.toString(player));
    }

    @Override
    protected void removeDisplay(Player player) {
        player.setPlayerListName(player.getName());
        player.setPlayerListHeader("");
        player.setPlayerListFooter("");
    }
}

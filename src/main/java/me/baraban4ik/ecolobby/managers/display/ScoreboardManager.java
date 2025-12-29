package me.baraban4ik.ecolobby.managers.display;

import fr.mrmicky.fastboard.FastBoard;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.config.files.modules.ScoreboardConfig;
import me.baraban4ik.ecolobby.message.FormattedMessage;
import org.bukkit.entity.Player;

public class ScoreboardManager extends AbstractDisplayManager<FastBoard> {

    private final ScoreboardConfig scoreboardConfig;

    public ScoreboardManager() {
        scoreboardConfig = ConfigManager.getScoreboardConfig();
    }

    @Override
    public boolean isEnabled() {
        return scoreboardConfig.isEnabled();
    }

    @Override
    protected long getRefreshTicks() {
        return scoreboardConfig.getRefresh() * 20L;
    }

    @Override
    protected void createDisplay(Player player) {
        FastBoard board = new FastBoard(player);
        addPlayer(player, board);
    }

    @Override
    public void update(Player player) {
        FastBoard board = playerDisplays.get(player.getUniqueId());
        if (board == null) return;

        FormattedMessage title = scoreboardConfig.getTitle();
        FormattedMessage lines = scoreboardConfig.getLines();

        board.updateTitle(title.toString(player));
        board.updateLines(lines.toList(player));
    }

    @Override
    protected void removeDisplay(FastBoard display) {
        display.delete();
    }
}

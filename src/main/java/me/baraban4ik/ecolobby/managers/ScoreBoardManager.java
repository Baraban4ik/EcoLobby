package me.baraban4ik.ecolobby.managers;

import fr.mrmicky.fastboard.FastBoard;
import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.baraban4ik.ecolobby.utils.Configurations.scoreboardConfig;

public class ScoreBoardManager {
    private final Map<Player, FastBoard> boards = new HashMap<>();

    public void send(Player player) {
        if (!boards.containsKey(player)) {
            long refresh = scoreboardConfig.getLong(Path.SCOREBOARD_REFRESH.getPath(), 1);

            FastBoard board = new FastBoard(player);
            boards.put(player, board);

            Bukkit.getScheduler().runTaskTimer(EcoLobby.getInstance(), () -> update(player), 0L, refresh * 20L);
        }
    }

    public void update(Player player) {
        FastBoard board = boards.get(player);
        if (board != null) {
            String title = Format.format(scoreboardConfig.getString(Path.SCOREBOARD_TITLE.getPath()), player);
            List<String> lines = scoreboardConfig.getStringList(Path.SCOREBOARD_LINES.getPath()).stream()
                    .map(line -> Format.format(line, player))
                    .collect(Collectors.toList());

            board.updateTitle(title);
            board.updateLines(lines);

        }
    }

    public void removeAllPlayers() {
        for (FastBoard board : boards.values()) {
            board.delete();
        }
        boards.clear();
    }

}

package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.enums.Path;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.tablistConfig;

public class TabManager {

    public void update(Player player) {
        List<String> header = tablistConfig.getStringList(Path.TABLIST_HEADER.getPath());
        List<String> footer = tablistConfig.getStringList(Path.TABLIST_FOOTER.getPath());

        String prefix = Format.format(tablistConfig.getString(Path.TABLIST_PREFIX.getPath()), player);
        String suffix = Format.format(tablistConfig.getString(Path.TABLIST_SUFFIX.getPath()), player);

        player.setPlayerListName(Format.format(prefix, player) + player.getName() + Format.format(suffix, player));

        player.setPlayerListHeader(convert(header, player));
        player.setPlayerListFooter(convert(footer, player));
    }

    public void sendTablist() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(EcoLobby.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                update(player);
            }
        }, 0L, tablistConfig.getInt(Path.TABLIST_REFRESH.getPath()) * 20L);
    }

    private String convert(List<String> list, Player player) {
        String convertString = String.join("\n", list);
        return Format.format(convertString, player);
    }
}

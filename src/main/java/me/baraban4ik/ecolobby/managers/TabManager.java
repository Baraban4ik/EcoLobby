package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.enums.TablistPath;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.entity.Player;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.tablistConfig;

public class TabManager {

    public void update(Player player) {
        List<String> header = tablistConfig.getStringList(TablistPath.HEADER.getPath());
        List<String> footer = tablistConfig.getStringList(TablistPath.FOOTER.getPath());

        String prefix = tablistConfig.getString(TablistPath.PREFIX.getPath());
        String suffix = tablistConfig.getString(TablistPath.SUFFIX.getPath());

        player.setPlayerListName(Format.format(prefix, player) + player.getName() + Format.format(suffix, player));

        player.setPlayerListHeader(convert(header, player));
        player.setPlayerListFooter(convert(footer, player));
    }

    private String convert(List<String> list, Player player) {
        String convertString = String.join("\n", list);
        return Format.format(convertString, player);
    }
}

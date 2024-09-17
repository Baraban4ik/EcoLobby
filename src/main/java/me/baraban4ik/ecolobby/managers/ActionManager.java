package me.baraban4ik.ecolobby.managers;

import me.baraban4ik.ecolobby.EcoLobby;
import me.baraban4ik.ecolobby.actions.*;
import me.baraban4ik.ecolobby.enums.ActionType;
import me.baraban4ik.ecolobby.models.PlayerAction;
import me.baraban4ik.ecolobby.utils.Format;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.baraban4ik.ecolobby.utils.Format.replacePlaceholders;

public class ActionManager {

    private static final EnumMap<ActionType, PlayerAction> actionMap = new EnumMap<>(ActionType.class);

    static {
        actionMap.put(ActionType.PLAYER, new PlayerCommandAction());
        actionMap.put(ActionType.CONSOLE, new ConsoleCommandAction());
        actionMap.put(ActionType.SOUND, new SoundAction());
        actionMap.put(ActionType.CONNECT, new ConnectAction());
        actionMap.put(ActionType.MSG, new MessageAction());
        actionMap.put(ActionType.MM, new MiniMessageAction());
        actionMap.put(ActionType.BROADCAST, new BroadcastAction());
        actionMap.put(ActionType.TELEPORT_TO_SPAWN, new TeleportSpawnAction());
        actionMap.put(ActionType.TITLE, new TitleAction());
        actionMap.put(ActionType.ACTIONBAR, new ActionBarAction());
        actionMap.put(ActionType.HIDE_PLAYERS, new HidePlayersAction());
        actionMap.put(ActionType.SHOW_PLAYERS, new ShowPlayersAction());
    }

    private static final Pattern WAIT_PATTERN = Pattern.compile("\\[WAIT:(\\d+)]");

    public static void execute(Player player, List<String> actions) {
        actions.forEach(action -> {
            Matcher matcher = WAIT_PATTERN.matcher(action);
            if (matcher.find()) {
                int waitTime = Integer.parseInt(matcher.group(1));
                String finalAction = matcher.replaceAll("");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        processAction(player, finalAction);
                    }
                }.runTaskLater(EcoLobby.getInstance(), waitTime * 20L);
            }
            else processAction(player, action);

        });
    }


    private static void processAction(Player player, String action) {
        ActionType actionType = ActionType.getActionType(action);

        if (actionType == null) {
            player.sendMessage(Format.format(action, player));
            return;
        }

        action = replaceVoid(action, player, actionType.getTag());
        PlayerAction playerAction = actionMap.getOrDefault(actionType, (p, a) -> p.sendMessage(Format.format(a, p)));

        playerAction.execute(player, action);
    }

    private static String replaceVoid(String action, Player player, String target) {
        return replacePlaceholders(action.replace(target + " ", "").replace(target, ""), player);
    }
}
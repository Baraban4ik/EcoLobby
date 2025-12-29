package me.baraban4ik.ecolobby.config.files;

import lombok.Getter;
import me.baraban4ik.ecolobby.config.AbstractConfig;
import me.baraban4ik.ecolobby.config.ConfigManager;
import me.baraban4ik.ecolobby.enums.Language;
import me.baraban4ik.ecolobby.enums.paths.FilePath;
import me.baraban4ik.ecolobby.enums.paths.MessagesPath;
import me.baraban4ik.ecolobby.message.FormattedMessage;


@Getter
public class MessagesConfig extends AbstractConfig {

    private String prefix;
    private FormattedMessage noPermission;
    private FormattedMessage whitelistKick;
    private FormattedMessage blacklistKick;
    private FormattedMessage denyChat;
    private FormattedMessage denyCommands;
    private FormattedMessage denyBreakBlocks;
    private FormattedMessage denyPlaceBlocks;
    private FormattedMessage denyInteractBlocks;
    private FormattedMessage teleportedSpawn;
    private FormattedMessage nullSpawn;

    @Override
    protected void loadValues() {
        prefix = config.getString(MessagesPath.PREFIX.get());
        noPermission = getFormattedMessage(MessagesPath.NO_PERMISSION);

        whitelistKick = getFormattedMessage(MessagesPath.WHITELIST_KICK);
        blacklistKick = getFormattedMessage(MessagesPath.BLACKLIST_KICK);

        denyChat = getFormattedMessage(MessagesPath.DENY_CHAT);
        denyCommands = getFormattedMessage(MessagesPath.DENY_COMMANDS);

        denyBreakBlocks = getFormattedMessage(MessagesPath.DENY_BREAK_BLOCKS);
        denyPlaceBlocks = getFormattedMessage(MessagesPath.DENY_PLACE_BLOCKS);
        denyInteractBlocks = getFormattedMessage(MessagesPath.DENY_INTERACT_BLOCKS);

        teleportedSpawn = getFormattedMessage(MessagesPath.TELEPORTED_SPAWN);
        nullSpawn = getFormattedMessage(MessagesPath.NULL_SPAWN);
    }

    @Override
    public String getPath() {
        Language language = ConfigManager.getConfig().getLanguage();
        return FilePath.MESSAGES.get() + language.getSuffix();
    }
}

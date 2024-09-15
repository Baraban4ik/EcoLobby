package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.enums.ConfigPath;
import me.baraban4ik.ecolobby.enums.MessagesPath;
import me.baraban4ik.ecolobby.utils.Chat;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.baraban4ik.ecolobby.EcoLobby.*;

public class PlayerListener implements Listener {


    @EventHandler
    public void updatePlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        double maxHealth = config.getDouble(ConfigPath.PLAYER_HEALTH.getPath(), 20);
        List<String> effects = config.getStringList(ConfigPath.PLAYER_EFFECTS.getPath());

        GameMode gameMode = GameMode.valueOf(config.getString(ConfigPath.PLAYER_GAMEMODE.getPath(), "SURVIVAL").toUpperCase());

        player.setGameMode(gameMode);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.setHealth(maxHealth);

        player.setFoodLevel(config.getInt(ConfigPath.PLAYER_FOOD_LEVEL.getPath()));
        player.setLevel(config.getInt(ConfigPath.PLAYER_LEVEL_EXP.getPath()));

        for (PotionEffect activeEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activeEffect.getType());
        }

        for (String effect : effects) {
            String[] effectSplit = effect.split(":");

            PotionEffectType type = PotionEffectType.getByName(effectSplit[0].toUpperCase());
            if (type == null) continue;

            int level = Integer.parseInt(effectSplit[1]);
            int time = Integer.parseInt(effectSplit[2]) * 20;

            boolean particles = Boolean.parseBoolean(effectSplit[3]);

            int duration = (time == 0) ? -1 : time;

            if (getInstance().getServerVersion() < 1.19) {
                duration = (time == 0) ? Integer.MAX_VALUE : time;
            }

            player.addPotionEffect(new PotionEffect(type, duration, level-1, false, particles));
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setAllowFlight(config.getBoolean(ConfigPath.ABILITIES_FLY.getPath()));
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.chat")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_CHAT.getPath())) {
            Chat.sendPathMessage(MessagesPath.DENY_CHAT.getPath(), player);
            event.setCancelled(true);
        }
    }
    @EventHandler()
    public void onCommands(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.commands")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_COMMANDS.getPath())) {
            String[] command = event.getMessage().replace("/", "").split(" ");
            List<String> allowedCommands = config.getStringList(ConfigPath.ABILITIES_COMMANDS_ALLOWED.getPath());

            if (!allowedCommands.contains(command[0])) {
                Chat.sendPathMessage(MessagesPath.DENY_COMMANDS.getPath(), player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerCommandTab(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.commands")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_COMMANDS.getPath())) {
            List<String> allowedCommands = config.getStringList(ConfigPath.ABILITIES_COMMANDS_ALLOWED.getPath());

            if (!config.getBoolean(ConfigPath.ABILITIES_COMMANDS_TAB_COMPLETE.getPath())) {
                event.getCommands().removeIf(command -> !allowedCommands.contains(command));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(config.getBoolean(ConfigPath.ABILITIES_DAMAGE.getPath()));
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(config.getBoolean(ConfigPath.ABILITIES_HUNGER.getPath()));
    }
    @EventHandler
    public void onMovements(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.movements")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_MOVEMENTS.getPath())) {
            Location from = event.getFrom().clone();
            Location to = event.getTo();

            from.setYaw(to.getYaw());
            from.setPitch(to.getPitch());

            if (!from.equals(to)) event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.break")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_BREAK.getPath())) {
            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_PARTICLE_EFFECT.getPath()))
                player.playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);

            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_MESSAGE.getPath()))
                Chat.sendPathMessage(MessagesPath.DENY_BREAK_BLOCKS.getPath(), player);

            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND.getPath())) {
                Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_ITEMS_DENY_SOUND_SOUND.getPath()));
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }

            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.place")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_PLACE.getPath())) {
            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_PARTICLE_EFFECT.getPath()))
                player.playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);

            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_MESSAGE.getPath()))
                Chat.sendPathMessage(MessagesPath.DENY_PLACE_BLOCKS.getPath(), player);

            if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND.getPath())) {
                Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND_SOUND.getPath()));
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }

            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlocksInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.blocks.interact")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_INTERACT.getPath())) {
            Block block = event.getClickedBlock();
            if (block == null) return;

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!block.getType().isInteractable()) return;

                if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_PARTICLE_EFFECT.getPath())) {
                    player.playEffect(event.getClickedBlock().getLocation(), Effect.SMOKE, BlockFace.UP);

                    if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_MESSAGE.getPath())) {
                        Chat.sendPathMessage(MessagesPath.DENY_INTERACT_BLOCKS.getPath(), player);

                        if (config.getBoolean(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND.getPath())) {
                            Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND_SOUND.getPath()));
                            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                        }

                        event.setCancelled(true);
                    } else if (event.getAction() == Action.PHYSICAL) {
                        if (block.getType().equals(Material.FARMLAND))
                            event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onMoveItems(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasPermission("ecolobby.bypass.items.move")) return;

            if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_MOVE.getPath())) {
                if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_DENY_SOUND.getPath())) {
                    Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_BLOCKS_DENY_SOUND_SOUND.getPath()));
                    player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ecolobby.bypass.items.drop")) return;

        if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_DROP.getPath())) {
            if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_DENY_SOUND.getPath())) {
                Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_ITEMS_DENY_SOUND_SOUND.getPath()));
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItems(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.hasPermission("ecolobby.bypass.items.pickup")) return;

            if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_PICKUP.getPath())) {
                if (config.getBoolean(ConfigPath.ABILITIES_ITEMS_DENY_SOUND.getPath())) {
                    Sound sound = Sound.valueOf(config.getString(ConfigPath.ABILITIES_ITEMS_DENY_SOUND_SOUND.getPath()));
                    player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                }
                event.setCancelled(true);
            }
        }
    }
}



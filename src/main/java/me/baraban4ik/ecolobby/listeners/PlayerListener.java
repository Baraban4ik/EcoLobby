package me.baraban4ik.ecolobby.listeners;

import me.baraban4ik.ecolobby.configurations.Configurations;
import me.baraban4ik.ecolobby.utils.spawnUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

import static me.baraban4ik.ecolobby.utils.chatUtils.getLang;
import static me.baraban4ik.ecolobby.utils.chatUtils.sendMessage;

public class PlayerListener implements Listener {
    private final Configurations config;

    public PlayerListener(Configurations configurations) {
        config = configurations;
    }

    // Chat and Commands:
    @EventHandler
    public void Chat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("ecolobby.bypass.chat") && !config.get("config.yml").getBoolean("Player.enable-chat")) {
            sendMessage(player, getLang().getString("disable-chat"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void Commands(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (!config.get("config.yml").getBoolean("Player.enable-commands") && !player.hasPermission("ecolobby.bypass.commands")) {
            String[] message = e.getMessage().replace("/", "").split(" ");
            if (!config.get("config.yml").getStringList("Player.use-commands").contains(message[0])) {
                sendMessage(player, getLang().getString("disable-commands"));
                e.setCancelled(true);
            }
        }
    }

    // Fly:
    @EventHandler
    public void Fly(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (config.get("config.yml").getBoolean("Player.enable-fly")) {
            player.setFlying(true);
        }
    }

    // Damage:
    @EventHandler
    public void Damage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && !config.get("config.yml").getBoolean("Player.enable-damage")) {
            e.setCancelled(true);
        }
    }

    // Break and Place blocks:
    @EventHandler
    public void BreakBlock(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("ecolobby.bypass.break")) {
            if (!config.get("config.yml").getBoolean("Player.enable-break-blocks")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("ecolobby.bypass.place")) {
            if (!config.get("config.yml").getBoolean("Player.enable-place-blocks")) {
                e.setCancelled(true);
            }
        }
    }

    // Hunger:
    @EventHandler
    public void Hunger(FoodLevelChangeEvent e) {
        if (!config.get("config.yml").getBoolean("Player.enable-hunger")) {
            e.setCancelled(true);
        }
    }

    // Movements:
    @EventHandler
    public void Movements(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!config.get("config.yml").getBoolean("Player.enable-movements")) {
            if (!player.hasPermission("ecolobby.bypass.move")) {
                Location from = e.getFrom().clone();
                Location to = e.getTo();
                assert to != null;
                from.setYaw(to.getYaw());
                from.setPitch(to.getPitch());
                if (!from.equals(to)) e.setCancelled(true);
            }
        }
    }

    // Jump to void
    @EventHandler
    public void JumpVoid(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (config.get("config.yml").getBoolean("Player.jump-to-void.enabled")) {
            if (player.getLocation().getY() <= config.get("config.yml").getDouble("Player.jump-to-void.height") && config.get("spawn.yml").getString("spawn.x") != null && config.get("spawn.yml").getString("spawn.y") != null) {
                spawnUtils.tpSpawn(player);
                sendMessage(player, getLang().getString("jump-to-void"));
            }
        }
    }

    // Statistics:
    @EventHandler
    public void Statistics(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        player.setGameMode(GameMode.valueOf(Objects.requireNonNull(config.get("config.yml").getString("Player.gamemode")).toUpperCase()));

        player.setLevel(config.get("config.yml").getInt("Player.level-exp"));
        player.setHealth(config.get("config.yml").getDouble("Player.health"));

        int newSpeed = Math.min(Math.max(config.get("config.yml").getInt("Player.speed", 2), -10), 10);
        player.setWalkSpeed(newSpeed * 0.1f);

        List<String> effects = config.get("config.yml").getStringList("Player.effects");
        effects.forEach((x) -> {
            PotionEffectType types = PotionEffectType.getByName(x.split(":")[0].toUpperCase());
            int level = Integer.parseInt(x.split(":")[1]) - 1;
            assert types != null;
            player.addPotionEffect(new PotionEffect(types, 9999999, level));
        });
    }
}

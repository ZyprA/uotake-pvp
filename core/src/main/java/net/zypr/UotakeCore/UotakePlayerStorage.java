package net.zypr.UotakeCore;

import it.unimi.dsi.fastutil.Hash;
import net.kyori.adventure.text.Component;
import net.zypr.UotakeCore.sql.DBController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;

public class UotakePlayerStorage implements Listener {
    private final HashMap<UUID, UotakePlayer> playerList;
    private final DBController dbController;

    public UotakePlayerStorage(DBController dbController)
    {
        this.dbController = dbController;
        this.playerList = new HashMap<>();
    }

    @NotNull
    public HashSet<UotakePlayer> getUotakePlayers() {
        return new HashSet<>(playerList.values());
    }

    @NotNull
    public UotakePlayer getUotakePlayer(@NotNull Player player) {
        // Must: DBとの連携
        return this.playerList.g(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoinEvent(PlayerJoinEvent e) {
        UotakePlayer uotakePlayer = new UotakePlayer(e.getPlayer());
        try {
            uotakePlayer = dbController.fetchPlayer(e.getPlayer(), true);
        } catch (SQLException ex) {
            e.getPlayer().kick(Component.text("データに不具合が発生しました" + ex));
        }
        this.playerList.put(e.getPlayer().getUniqueId(), uotakePlayer);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent e) {
        UotakePlayer uotakePlayer = playerList.getOrDefault(e.getPlayer().getUniqueId(), null);
        if (uotakePlayer == null) return;
        try {
            dbController.savePlayerStatus(uotakePlayer, false);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        this.playerList.remove(e.getPlayer().getUniqueId());
    }


}

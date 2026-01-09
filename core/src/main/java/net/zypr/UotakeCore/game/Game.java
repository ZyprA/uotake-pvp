package net.zypr.UotakeCore.game;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.zypr.Fukurou.api.game.GameInstance;
import net.zypr.Fukurou.api.phase.GamePhase;
import net.zypr.UotakeCore.UotakePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public abstract class Game extends GameInstance {

    private final HashMap<UUID, UotakePlayer> gamePlayers = new HashMap<>();

    public final void addPlayers(HashSet<UotakePlayer> players) {
        players.forEach(this::addPlayer);
    }

    public final void addPlayer(UotakePlayer player) {
        this.gamePlayers.put(player.getPlayer().getUniqueId(), player);
    }

    public final void removePlayer(UUID uuid) {
        this.gamePlayers.remove(uuid);
    }

    public final void removeAllPlayers() {
        this.gamePlayers.clear();
    }

    public final List<UotakePlayer> getUotakePlayers() {
        return this.gamePlayers.values().stream().toList();
    }

    public final List<Player> getPlayers() {
        return this.gamePlayers.values().stream().map(UotakePlayer::getPlayer).toList();
    }

    @Override
    public final int gameTick() {
        return 1;
    }

    public abstract Component getPrefix();


     public final void broadcast(Component text) {
         this.getPlayers().forEach(player -> sendMessage(player, text));
     }

     public final void sendMessage(Player player, Component text) {
         player.sendMessage(Component.join(JoinConfiguration.spaces(), getPrefix(), text));
     }
}

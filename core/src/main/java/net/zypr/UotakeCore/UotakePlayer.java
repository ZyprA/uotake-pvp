package net.zypr.UotakeCore;

import net.zypr.UotakeCore.weapon.Weapon;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UotakePlayer {
    private final Player player;
    private final List<Weapon> weapons;
    private final PlayerStatistics playerStatistics;

    public UotakePlayer(Player player) {
        this.player = player;
        this.weapons = new ArrayList<>();
        this.playerStatistics = new PlayerStatistics();
    }

    public UotakePlayer(Player player, List<Weapon> weapons, PlayerStatistics playerStatistics) {
        this.player = player;
        this.weapons = weapons;
        this.playerStatistics = playerStatistics;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }
}

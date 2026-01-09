package net.zypr.UotakeCore.sql;

import net.zypr.UotakeCore.PlayerStatistics;
import net.zypr.UotakeCore.UotakePlayer;
import net.zypr.UotakeCore.weapon.Weapon;
import net.zypr.UotakeCore.weapon.WeaponRepository;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    private final DBSource dbSource;
    private final WeaponRepository weaponRepository;

    public DBController(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
        this.dbSource = new DBSource();
    }

    public UotakePlayer fetchPlayer(@NotNull Player player, Boolean use) throws SQLException {
        try (Connection conn = dbSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM player WHERE player.uuid = ? LIMIT 1");
            ps.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return new UotakePlayer(player);
                }
                if (!rs.getBoolean("is_latest")) {
                    throw new SQLTransactionRollbackException("Not latest data!");
                }
                PlayerStatistics playerStatistics = new PlayerStatistics(
                        rs.getInt("kills"),
                        rs.getInt("deaths"),
                        rs.getInt("wins"),
                        rs.getInt("loses")
                );
                if (use) {
                    PreparedStatement ps_use = conn.prepareStatement("UPDATE player SET is_latest = 0 WHERE player.uuid = ?");
                    ps_use.setString(1, player.getUniqueId().toString());
                    ps_use.execute();
                }
                return new UotakePlayer(player, fetchWeapons(rs.getInt("id")), playerStatistics);
            }
        }
    }

    public void savePlayerStatus(@NotNull UotakePlayer uotakePlayer, Boolean continue_use) throws SQLException {
        try (Connection conn = dbSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    """
                            INSERT INTO player (uuid, kills, deaths, wins, loses, is_latest)
                            VALUES (?, ?, ?, ?, ?, ?)
                            ON DUPLICATE KEY UPDATE
                            kills = ?,
                            deaths = ?,
                            wins = ?,
                            loses = ?,
                            is_latest = ?
                            """
            );
            PlayerStatistics playerStatistics = uotakePlayer.getPlayerStatistics();
            ps.setString(1,uotakePlayer.getPlayer().getUniqueId().toString());
            ps.setInt(2,playerStatistics.getKills());
            ps.setInt(3,playerStatistics.getDeaths());
            ps.setInt(4,playerStatistics.getWins());
            ps.setInt(5,playerStatistics.getDeaths());
            ps.setBoolean(6, !continue_use);
            ps.setInt(7,playerStatistics.getKills());
            ps.setInt(8,playerStatistics.getDeaths());
            ps.setInt(9,playerStatistics.getWins());
            ps.setInt(10,playerStatistics.getDeaths());
            ps.setBoolean(11, !continue_use);
            ps.execute();
        }
    }

    private List<Weapon> fetchWeapons(int playerId) {
        try (Connection conn = dbSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM player_weapon WHERE player_weapon.player_id = ?");
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Weapon> weapons = new ArrayList<>();
                while (rs.next()) {
                    weapons.add(weaponRepository.getWeapon(rs.getString("weapon_name")));
                }
                return weapons;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

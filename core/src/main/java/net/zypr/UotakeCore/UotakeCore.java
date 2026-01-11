package net.zypr.UotakeCore;

import me.deecaad.weaponmechanics.WeaponMechanics;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.zypr.UotakeCore.game.Game;
import net.zypr.UotakeCore.game.GameBuilder;
import net.zypr.UotakeCore.game.GameScheduler;
import net.zypr.UotakeCore.sql.DBController;
import net.zypr.UotakeCore.weapon.Weapon;
import net.zypr.UotakeCore.weapon.WeaponRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public final class UotakeCore extends JavaPlugin {
    private static UotakeCore uotakeCore;
    private UotakePlayerStorage uotakePlayerStorage;
    private WeaponRepository weaponRepository;


    @Override
    public void onEnable() {
        uotakeCore = this;
        if (getServer().getPluginManager().getPlugin("WeaponMechanics") instanceof WeaponMechanics weaponMechanics) {
            this.weaponRepository = new WeaponRepository(weaponMechanics);
        } else {
            getServer().getConsoleSender().sendMessage(Component.join(JoinConfiguration.newlines(),
                    Component.text("############### WARNING ###############", NamedTextColor.RED),
                    Component.text("This plugin requires WeaponMechanics,", NamedTextColor.RED),
                    Component.text("Please install the latest WeaponMechanics plugin,", NamedTextColor.RED),
                    Component.text("or this plugin will be disabled.", NamedTextColor.RED)
            ));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        DBController dbController = new DBController(weaponRepository);
        this.uotakePlayerStorage = new UotakePlayerStorage(dbController);

        getServer().getPluginManager().registerEvents(this.uotakePlayerStorage, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Nullable
    public static UotakeCore getInstance() {
        return uotakeCore;
    }

    @NotNull
    public HashSet<UotakePlayer> getUotakePlayers() {
        return uotakePlayerStorage.getUotakePlayers();
    }

    @NotNull
    public UotakePlayer getUotakePlayer(@NotNull Player player) {
        return uotakePlayerStorage.getUotakePlayer(player);
    }

    @Nullable public Weapon getWeapon(String id) {
        return this.weaponRepository.getWeapon(id);
    }

    public <T extends Game> void register(GameBuilder<T> gameBuilder) {
        GameScheduler<T> gameScheduler = new GameScheduler<>(gameBuilder, this);
        gameScheduler.run();
    }



}

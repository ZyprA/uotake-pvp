package net.zypr.uotakeTDM;

import net.zypr.Fukurou.api.phase.GamePhase;
import net.zypr.UotakeCore.UotakeCore;
import net.zypr.UotakeCore.game.GameBuilder;
import net.zypr.UotakeCore.game.WaitingPhase;
import org.bukkit.plugin.java.JavaPlugin;

public final class UotakeTDM extends JavaPlugin {

    private UotakeCore uotakeCore;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info("Hello! from TDM");

        if (getServer().getPluginManager().getPlugin("UotakeCore") instanceof UotakeCore core) {
            this.uotakeCore = core;
        }

        GameBuilder<TDMGame> gameBuilder = new GameBuilder<>() {
            @Override
            public TDMGame getGame() {
                return new TDMGame();
            }

            @Override
            public GamePhase<TDMGame> getFirstPhase() {
                return null;
            }
        };

        uotakeCore.register(gameBuilder);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

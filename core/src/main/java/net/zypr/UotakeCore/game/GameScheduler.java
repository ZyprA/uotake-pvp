package net.zypr.UotakeCore.game;

import net.zypr.Fukurou.api.GameControllable;
import net.zypr.Fukurou.api.executor.GameExecutor;
import net.zypr.Fukurou.internal.GameExecutorImpl;
import net.zypr.UotakeCore.UotakeCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameScheduler<T extends Game> implements GameControllable, Listener {
    private final UotakeCore core;
    private final GameBuilder<T> gameBuilder;
    private GameExecutor<T> gameExecutor;

    public GameScheduler(GameBuilder<T> gameBuilder, UotakeCore core) {
        this.core = core;
        this.gameBuilder = gameBuilder;
        this.gameExecutor = new GameExecutorImpl<>(gameBuilder.getGame(), core, new WaitingPhase<>(gameBuilder.getFirstPhase(), core), this);
    }

    public void run() {
        core.getServer().getPluginManager().registerEvents(this, core);
        gameExecutor.start();
    }

    @Override
    public void onGameEnd() {
        this.gameExecutor = new GameExecutorImpl<>(gameBuilder.getGame(), core, new WaitingPhase<>(gameBuilder.getFirstPhase(), core), this);
        gameExecutor.start();
    }

    @EventHandler
    public void playerGameJoin(PlayerJoinEvent e) {
        if (gameBuilder == null) return;
        gameBuilder.getGame().addPlayer(core.getUotakePlayer(e.getPlayer()));
    }

    @EventHandler
    public void playerGameLeave(PlayerQuitEvent e) {
        if (gameBuilder == null) return;
        gameBuilder.getGame().removePlayer(e.getPlayer().getUniqueId());
    }
}

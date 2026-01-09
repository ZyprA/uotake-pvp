package net.zypr.UotakeCore.game;

import net.kyori.adventure.text.Component;
import net.zypr.Fukurou.api.game.GameInstance;
import net.zypr.Fukurou.api.phase.GamePhase;
import net.zypr.UotakeCore.UotakeCore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class WaitingPhase<T extends Game> extends GamePhase<T> {
    private final GamePhase<T> initPhase;
    private final UotakeCore core;

    public WaitingPhase(GamePhase<T> initPhase, UotakeCore core) {
        this.core = core;
        this.initPhase = initPhase;
    }
    @Override
    public Consumer<T> getInitialExecution() {
        return Game::removeAllPlayers;
    }

    @Override
    public Function<T, @Nullable GamePhase<T>> getExecution() {return game -> {
        game.broadcast(Component.text("Hello"));
        return new MapChoosePhase<>(initPhase);
    };
    }

    @Override
    public Function<T, List<Listener>> getListeners() {
        return null;
    }
}

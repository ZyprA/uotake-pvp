package net.zypr.UotakeCore.game;

import net.kyori.adventure.text.Component;
import net.zypr.Fukurou.api.phase.GamePhase;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MapChoosePhase<T extends Game> extends GamePhase<T> {

    private final GamePhase<T> nextPhase;

    public MapChoosePhase(GamePhase<T> nextPhase) {
        this.nextPhase = nextPhase;
    }

    @Override
    public Consumer<T> getInitialExecution() {
        return game -> {
            game.broadcast(Component.text("選択フェーズに入りました"));
        };
    }

    @Override
    public Function<T, @Nullable GamePhase<T>> getExecution() {
        return game -> {
            Bukkit.getLogger().info(String.valueOf(getTimer().getTick()));
            if (getTimer().getTick() > 500) {
                return nextPhase;
            } else if (getTimer().getTick() % 20 == 0) {
                game.broadcast(Component.text(getTimer().getTick() / 20));
            }

            return this;
        };
    }

    @Override
    public Function<T, List<Listener>> getListeners() {
        return null;
    }
}

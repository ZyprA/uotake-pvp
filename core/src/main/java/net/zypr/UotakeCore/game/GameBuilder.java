package net.zypr.UotakeCore.game;

import net.zypr.Fukurou.api.phase.GamePhase;

public abstract class GameBuilder<T extends Game> {


    public abstract T getGame();

    public abstract GamePhase<T> getFirstPhase();
}

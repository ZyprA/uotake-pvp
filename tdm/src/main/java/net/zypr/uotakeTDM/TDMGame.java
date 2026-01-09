package net.zypr.uotakeTDM;

import net.kyori.adventure.text.Component;
import net.zypr.UotakeCore.game.Game;

public class TDMGame extends Game {
    private final Component PREFIX = Component.text("TDM");

    @Override
    public Component getPrefix() {
        return PREFIX;
    }

}

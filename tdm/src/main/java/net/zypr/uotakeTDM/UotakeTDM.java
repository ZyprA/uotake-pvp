package net.zypr.uotakeTDM;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
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

        if (getServer().getPluginManager().getPlugin("UotakeCore") instanceof UotakeCore core) {
            this.uotakeCore = core;
            getServer().getConsoleSender().sendMessage(Component.text("UotakeCore was found. UotakeTDM Plugin is enabled.", NamedTextColor.GREEN));
        } else {
            getServer().getConsoleSender().sendMessage(Component.join(JoinConfiguration.newlines(),
                    Component.text("############### WARNING ###############", NamedTextColor.RED),
                    Component.text("This plugin requires UotakeCore,", NamedTextColor.RED),
                    Component.text("Please install the latest UotakeCore plugin,", NamedTextColor.RED),
                    Component.text("or this plugin will be disabled.", NamedTextColor.RED)
                    ));
            getServer().getPluginManager().disablePlugin(this);
            return;
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

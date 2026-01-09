package net.zypr.UotakeCore;

public class PlayerStatistics {
    private int kills;
    private int deaths;
    private int wins;
    private int loses;

    public PlayerStatistics() {
        this.kills = 0;
        this.deaths = 0;
        this.wins = 0;
        this.loses = 0;
    }

    public PlayerStatistics(int kills, int deaths, int wins, int loses) {
        this.kills = kills;
        this.deaths = deaths;
        this.wins = wins;
        this.loses = loses;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}

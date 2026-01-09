package net.zypr.UotakeCore.weapon;

public class Weapon {
    private final String id;
    private final String displayName;

    protected Weapon(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

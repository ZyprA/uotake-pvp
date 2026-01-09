package net.zypr.UotakeCore.weapon;

public enum WeaponType {
    AR("アサルトライフル", WeaponCategory.MAIN),
    SR("スナイパーライフル", WeaponCategory.MAIN),
    SMG("サブマシンガン", WeaponCategory.MAIN),
    SG("ショットガン", WeaponCategory.MAIN),
    HG("ハンドガン", WeaponCategory.SUB),
    KNIFE("ナイフ", WeaponCategory.SUB),
    FOOD("食料", WeaponCategory.SUPPORT),
    OTHER("その他", WeaponCategory.OTHER);

    private final String displayName;
    private final WeaponCategory weaponCategory;

    WeaponType(String displayName, WeaponCategory weaponCategory) {
        this.displayName = displayName;
        this.weaponCategory = weaponCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static WeaponType of(String key) {
        return switch(key) {
            case "AR" -> AR;
            case "SR" -> SR;
            case "SMG" -> SMG;
            case "SG" -> SG;
            case "HG" -> HG;
            case "KNIFE" -> KNIFE;
            case "FOOD" -> FOOD;
            default -> OTHER;
        };
    }

    public WeaponCategory getWeaponCategory() {
        return weaponCategory;
    }
}

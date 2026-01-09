package net.zypr.UotakeCore.weapon;

import me.deecaad.core.file.Configuration;
import me.deecaad.weaponmechanics.WeaponMechanics;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Set;

public class WeaponRepository {
    private final HashMap<String, Weapon> weaponList;
    private final WeaponMechanics weaponMechanics;

    public WeaponRepository(WeaponMechanics weaponMechanics) {
        this.weaponMechanics = weaponMechanics;
        this.weaponList = new HashMap<>();
    }

    public void load() {
        weaponList.clear();
        Configuration config = weaponMechanics.getWeaponConfigurations();
        Set<String> identifications = config.keys();
        identifications.forEach(id -> {
            String display = config.getString(id + ".Uotake.Display", id);
            Weapon weapon = new Weapon(id, display);
            weaponList.put(id, weapon);
        });
    }

    @Nullable
    public Weapon getWeapon(String id) {
        return weaponList.getOrDefault(id, null);
    }

}

package com.pcrewards;

public enum PcRewardOptions {
    ATTACK("Attack"),
    STRENGTH("Strength"),
    DEFENCE("Defence"),
    RANGE("Ranged"),
    MAGIC("Magic"),
    HITPOINTS("Hitpoints"),
    PRAYER("Prayer");


    private final String abbrev;

    PcRewardOptions(String abbrev) {
        this.abbrev = abbrev;
    }

    @Override
    public String toString() {
        return abbrev;
    }
}

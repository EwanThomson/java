package com.ewan;

import static org.junit.jupiter.api.Assertions.*;

class FlardQuestTest {
    @org.junit.jupiter.api.Test
    void armor() {
        Heavy heavy = new Heavy();
        assertEquals(8, heavy.reducedDamage(10));

        Light light = new Light();
        assertEquals(5, light.reducedDamage(10));

        Ultimate ultimate = new Ultimate();
        assertEquals(3, ultimate.reducedDamage(10));
        ultimate.upgradeFlat();
        assertEquals(2, ultimate.reducedDamage(10));
        ultimate.upgradePercent();
        assertEquals(74, ultimate.reducedDamage(100));
    }
}
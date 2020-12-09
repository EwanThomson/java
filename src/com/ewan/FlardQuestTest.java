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
    }
}
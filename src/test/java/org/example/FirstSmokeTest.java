package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FirstSmokeTest {

    @Test
    void ilkTest_basitToplama() {
        int sonuc = 2 + 2;
        assertEquals(4, sonuc, "2+2 dört etmeli");
    }

    @Test
    void ikinciTest_stringKontrolu() {
        String mesaj = "e-ihale otomasyonu";
        assertTrue(mesaj.contains("ihale"));
    }
}

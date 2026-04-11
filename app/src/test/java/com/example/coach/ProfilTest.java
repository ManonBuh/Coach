package com.example.coach;

import com.example.coach.model.Profil;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class ProfilTest {

    private final Profil profilMaigre = new Profil(45, 170, 20, 0, new Date());
    private final Profil profilNormal = new Profil(70, 180, 40, 1, new Date());
    private final Profil profilGras = new Profil(67, 165, 35, 0, new Date());

    @Test
    public void testGetMessage() {
        assertEquals("trop faible", profilMaigre.getMessage());
        assertEquals("normal", profilNormal.getMessage());
        assertEquals("trop élevé", profilGras.getMessage());
    }

    @Test
    public void testGetImage() {
        assertEquals("maigre", profilMaigre.getImage());
        assertEquals("normal", profilNormal.getImage());
        assertEquals("graisse", profilGras.getImage());
    }

    @Test
    public void testGetImg() {
        assertEquals(17.9, profilMaigre.getImg(), 0.1);
        assertEquals(18.9, profilNormal.getImg(), 0.1);
        assertEquals(32.2, profilGras.getImg(), 0.1);
    }

    @Test
    public void testNormal() {
        assertFalse(profilMaigre.normal());
        assertTrue(profilNormal.normal());
        assertFalse(profilGras.normal());
    }
}
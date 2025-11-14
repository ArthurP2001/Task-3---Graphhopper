package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GHIntObjectHashMapTest {

    /**
     * Nom : testRemoveKeyReturnsNull
     * Intention : Vérifie qu'après suppression d'une clé, la méthode get retourne null.
     * Données : clé = 7, valeur = "TestValue".
     * Oracle : get(7) == null après remove(7).
     */
    @Test
    public void testRemoveKeyReturnsNull() {
        GHIntObjectHashMap<String> map = new GHIntObjectHashMap<>();
        map.put(7, "TestValue");
        map.remove(7);
        assertNull(map.get(7));
    }

    /**
     * Nom : testOverrideValue
     * Intention : Vérifie que l'insertion d'une même clé remplace la valeur précédente.
     * Données : clé = 1, valeur1 = "OldValue", valeur2 = "NewValue".
     * Oracle : get(1) == "NewValue".
     */
    @Test
    public void testOverrideValue() {
        GHIntObjectHashMap<String> map = new GHIntObjectHashMap<>();
        map.put(1, "OldValue");
        map.put(1, "NewValue");
        assertEquals("NewValue", map.get(1));
    }
}


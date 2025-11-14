package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GHLongHashSetTest {

    /**
     * Nom : testAddDuplicate
     * Intention : Vérifie qu’ajouter un élément déjà présent ne modifie pas la taille du set.
     * Données : élément = 42L.
     * Oracle : size() reste identique après ajout d’un doublon.
     */
    @Test
    public void testAddDuplicate() {
        GHLongHashSet set = new GHLongHashSet();
        set.add(42L);
        int initialSize = set.size();
        set.add(42L);
        assertEquals(initialSize, set.size());
    }

    /**
     * Nom : testContainsAfterClear
     * Intention : Vérifie qu’un élément n’est plus présent après clear().
     * Données : élément = 9L.
     * Oracle : contains(9L) == false après clear().
     */
    @Test
    public void testContainsAfterClear() {
        GHLongHashSet set = new GHLongHashSet();
        set.add(9L);
        set.clear();
        assertFalse(set.contains(9L));
    }
}


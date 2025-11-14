package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tâche 3 — Test Mockito pour GHSortedCollection
 * ----------------------------------------------
 * Objectif :
 * - Vérifier les insertions, le tri naturel via TreeMap, et la suppression.
 * - Utiliser un spy Mockito pour observer les insertions.
 */
public class GHSortedCollectionMockitoTest {

    @Test
    void testInsertAndPeekValue() {
        // Étape 1 : Création d’un spy sur la vraie collection
        GHSortedCollection coll = spy(new GHSortedCollection());

        // Étape 2 : Insertions désordonnées
        coll.insert(5, 50);
        coll.insert(2, 20);
        coll.insert(7, 70);
        coll.insert(1, 10);

        // Étape 3 : Vérifie la taille totale
        assertEquals(4, coll.getSize(), "La taille doit être égale au nombre d’insertions");

        // Étape 4 : Vérifie que TreeMap trie naturellement les valeurs
        assertEquals(10, coll.peekValue(), "peekValue() doit retourner la plus petite valeur");
        assertEquals(1, coll.peekKey(), "peekKey() doit retourner la clé correspondant à la plus petite valeur");

        // Étape 5 : Vérifie que la méthode insert a bien été appelée 4 fois
        verify(coll, times(4)).insert(anyInt(), anyInt());
    }

    @Test
    void testPollKeyRemovesMinEntry() {
        // Étape 1 : Crée une vraie collection
        GHSortedCollection coll = new GHSortedCollection();

        // Étape 2 : Insertions
        coll.insert(3, 30);
        coll.insert(8, 80);
        coll.insert(1, 10);

        // Étape 3 : Vérifie le premier élément
        assertEquals(10, coll.peekValue(), "La plus petite valeur (10) doit être en tête");
        assertEquals(1, coll.peekKey(), "La clé du plus petit doit être 1");

        // Étape 4 : Supprime la plus petite entrée
        int removed = coll.pollKey();
        assertEquals(1, removed, "pollKey() doit retirer la clé associée à la plus petite valeur");

        // Étape 5 : Vérifie que la taille diminue
        assertEquals(2, coll.getSize(), "La taille doit diminuer après suppression");

        // Étape 6 : Vérifie la cohérence
        assertFalse(coll.isEmpty(), "La collection ne doit pas être vide après un seul poll");
    }
}

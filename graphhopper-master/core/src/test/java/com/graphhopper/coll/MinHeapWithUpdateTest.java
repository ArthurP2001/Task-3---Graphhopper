/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.graphhopper.coll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;



public class MinHeapWithUpdateTest implements BinaryHeapTestInterface {

    private MinHeapWithUpdate heap;

    @Override
    public void create(int capacity) {
        heap = new MinHeapWithUpdate(capacity);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void push(int id, float val) {
        heap.push(id, val);
    }

    boolean contains(int id) {
        return heap.contains(id);
    }

    @Override
    public int peekId() {
        return heap.peekId();
    }

    @Override
    public float peekVal() {
        return heap.peekValue();
    }

    @Override
    public void update(int id, float val) {
        heap.update(id, val);
    }

    @Override
    public int poll() {
        return heap.poll();
    }

    @Override
    public void clear() {
        heap.clear();
    }

    @Test
    public void outOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeapWithUpdate(4).push(4, 1.2f));
        assertThrows(IllegalArgumentException.class, () -> new MinHeapWithUpdate(4).push(-1, 1.2f));
    }

    @Test
    void tooManyElements() {
        create(3);
        push(1, 0.1f);
        push(2, 0.1f);
        push(0, 0.1f);
        // pushing element 1 again is not allowed (but this is not checked explicitly). however pushing more elements
        // than 3 is already an error
        assertThrows(IllegalStateException.class, () -> push(1, 0.1f));
        assertThrows(IllegalStateException.class, () -> push(2, 6.1f));
    }

    @Test
    void duplicateElements() {
        create(5);
        push(1, 0.2f);
        push(0, 0.4f);
        push(2, 0.1f);
        assertEquals(2, poll());
        // pushing 2 again is ok because it was polled before
        push(2, 0.6f);
        // but now its not ok to push it again
        assertThrows(IllegalStateException.class, () -> push(2, 0.4f));
    }

    @Test
    void testContains() {
        create(4);
        push(1, 0.1f);
        push(2, 0.7f);
        push(0, 0.5f);
        assertFalse(contains(3));
        assertTrue(contains(1));
        assertEquals(1, poll());
        assertFalse(contains(1));
    }

    @Test
    void containsAfterClear() {
        create(4);
        push(1, 0.1f);
        push(2, 0.1f);
        assertEquals(2, size());
        clear();
        assertFalse(contains(0));
        assertFalse(contains(1));
        assertFalse(contains(2));
    }

    /**
     * Vérifie que les éléments sont extraits dans le bon ordre de priorité.
     * Le plus petit élément (ayant la valeur la plus basse) doit être extrait en premier.
     * On insère ici trois éléments avec des priorités différentes,
     * puis on vérifie l’ordre de sortie croissant.
     */
    @Test
    public void testExtractMinOrder() {
        MinHeapWithUpdate heap = new MinHeapWithUpdate(10);
        heap.push(1, 5);  // id=1 avec priorité 5
        heap.push(2, 1);  // id=2 avec priorité 1 (doit sortir en premier)
        heap.push(3, 3);  // id=3 avec priorité 3

        // Vérifie que le heap extrait dans l'ordre croissant des priorités
        assertEquals(2, heap.poll()); // plus petit d'abord
        assertEquals(3, heap.poll()); // puis le suivant
        assertEquals(1, heap.poll()); // enfin le plus grand
    }


    /**
     * Vérifie que la mise à jour de la priorité d’un élément déjà présent dans le tas
     * est correctement prise en compte. Le nouvel ordre doit être réévalué.
     * Ce test simule une situation où un nœud (id=1) voit sa priorité diminuer,
     * ce qui doit le faire passer en tête du tas.
     */
    @Test
    public void testUpdatePriority() {
        MinHeapWithUpdate heap = new MinHeapWithUpdate(10);
        heap.push(1, 10); // priorité initiale haute
        heap.push(2, 20); // autre élément avec priorité plus élevée

        heap.update(1, 5); // on diminue la priorité de 1
        // Le nœud 1 doit maintenant devenir le minimum
        assertEquals(1, heap.poll());
    }


    /**
     * Teste l’insertion de plusieurs valeurs générées aléatoirement à l’aide de Faker.
     * L’objectif est de vérifier que l’insertion dans le tas augmente bien la taille
     * du heap et qu’aucune exception n’est levée pendant l’opération.
     * On ne teste pas le contenu exact ici, mais la cohérence du nombre d’éléments insérés.
     */
    @Test
    public void testInsertRandomValuesUsingFaker() {
        Faker faker = new Faker();
        MinHeapWithUpdate heap = new MinHeapWithUpdate(20);

        // Insertion de 5 éléments avec identifiants uniques et valeurs croissantes
        for (int i = 0; i < 5; i++) {
            String randomName = faker.name().firstName(); // valeur symbolique
            heap.push(i, i);
        }

        // Vérifie que le nombre d’éléments stockés correspond aux insertions
        assertEquals(5, heap.size());
    }


}





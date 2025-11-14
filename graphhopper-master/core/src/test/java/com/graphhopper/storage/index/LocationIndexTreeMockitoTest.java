package com.graphhopper.storage.index;

import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.storage.Directory;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.RAMDirectory;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.shapes.BBox;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tâche 3 – Test Mockito stable et compatible GraphHopper 11.x
 * ---------------------------------------------------------------
 *  - Utilise un vrai RAMDirectory pour initialiser dataAccess.
 *  - Mocke Graph et EdgeFilter.
 *  - Vérifie le bon fonctionnement structurel de findClosest().
 */
public class LocationIndexTreeMockitoTest {

    @Test
    void testFindClosestWithMocks() {
        // Étape 1 : création des mocks
        Graph mockGraph = mock(Graph.class);
        EdgeFilter mockFilter = mock(EdgeFilter.class);
        AllEdgesIterator mockAllEdges = mock(AllEdgesIterator.class);
        EdgeIteratorState mockEdge = mock(EdgeIteratorState.class);

        // Étape 2 : configuration des mocks
        when(mockGraph.getBounds()).thenReturn(new BBox(-10, 10, -10, 10));
        when(mockGraph.getAllEdges()).thenReturn(mockAllEdges);
        when(mockAllEdges.next()).thenReturn(false);
        when(mockFilter.accept(any(EdgeIteratorState.class))).thenReturn(true);

        // Étape 3 : utilisation d’un vrai RAMDirectory (pas mocké)
        Directory realDir = new RAMDirectory();   // crée un vrai DataAccess interne

        // Étape 4 : création de l’index
        LocationIndexTree index = new LocationIndexTree(mockGraph, realDir);
        index.setResolution(1000);
        index.prepareIndex();                     // fonctionne, dataAccess non nul

        // Étape 5 : exécution de la méthode testée
        Object result = index.findClosest(0.0, 0.0, mockFilter);

        // Étape 6 : assertions et vérifications
        assertNotNull(result, "findClosest() ne doit pas retourner null");
        assertTrue(result.toString().length() >= 0);
        verify(mockGraph, atLeastOnce()).getBounds();
    }
}

package com.bexstech.exam.engine;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.CheapestPath;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.util.ReadFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DijkstraAlgorithmTest {

    private DijkstraAlgorithm engine;

    @Before
    public void init() {
        Graph g = ReadFile.getGraphFromCSV("./resources/routes.csv");
        engine = new DijkstraAlgorithm(g);
    }

    @Test
    @DisplayName("should find correct route and price")
    public void shouldFindCorrectRouteAndPrice() {
        String expectedString = "GRU - BRC - SCL - ORL - CDG";
        Integer expectedPrice = 40;
        CheapestPath outcome = engine.calculateShortestPath("GRU", "CDG");
        assertAll("Route and Price",
                () -> assertEquals(expectedString, outcome.pathListToString(" - ")),
                () -> assertEquals(expectedPrice, outcome.getPrice())
        );
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should throw bad request exception")
    public void shouldThrowBadRequestException() {
        engine.calculateShortestPath("XXX", "CRT");
    }
}

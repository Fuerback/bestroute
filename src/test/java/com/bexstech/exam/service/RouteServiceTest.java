package com.bexstech.exam.service;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.singleton.RouteSingleton;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.WriteFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;

    @Before
    public void init() {
        Graph graph = ReadFile.getGraphFromCSV("./resources/routes.csv");
        RouteSingleton.getInstance().updateGraph(graph);
    }

    @Test
    @DisplayName("should find route")
    public void shouldFindRoute() {
        String routeInput = "GRU-CDG";

        String cheapestRoute = routeService.find(RouteSingleton.getInstance().getGraph(), routeInput).toString();

        assertThat(cheapestRoute).isEqualTo("GRU - BRC - SCL - ORL - CDG > $40");
    }

    @Test
    @DisplayName("should find route and ignore cyclic routes")
    public void shouldFindRouteAndIgnoreCyclicRoute() {
        String routeInput = "GRU-CRT";

        String cheapestRoute = routeService.find(RouteSingleton.getInstance().getGraph(), routeInput).toString();

        assertThat(cheapestRoute).isEqualTo("GRU - FLN - CRT > $50");
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should ignore cyclic route")
    public void shouldIgnoreCyclicRoute() {
        String routeInput = "GRU-GRU";

        routeService.find(RouteSingleton.getInstance().getGraph(), routeInput);
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should ignore empty route")
    public void shouldIgnoreEmptyRoute() {
        String routeInput = "";

        routeService.find(RouteSingleton.getInstance().getGraph(), routeInput);
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should ignore numeric route")
    public void shouldIgnoreNumericRoute() {
        String routeInput = "G1R-CDG";

        routeService.find(RouteSingleton.getInstance().getGraph(), routeInput);
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should ignore unknown route")
    public void shouldIgnoreUnknownRoute() {
        String routeInput = "GRU-XXX";

        routeService.find(RouteSingleton.getInstance().getGraph(), routeInput);
    }

    @Test
    @DisplayName("should insert new route")
    public void shouldInsertNewRoute() throws IOException {
        try (MockedStatic<WriteFile> mockedUuid = Mockito.mockStatic(WriteFile.class)) {
            RouteDTO newRoute = new RouteDTO("FLN", "BEL", 35);

            routeService.insert(newRoute);
        }
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should not insert route with no price")
    public void shouldNotInsertRouteWithNoPrice() throws IOException {
        RouteDTO newRoute = new RouteDTO("FLN", "BEL", null);

        routeService.insert(newRoute);
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should not insert cyclic route")
    public void shouldNotInsertCyclicRoute() throws IOException {
        RouteDTO newRoute = new RouteDTO("FLN", "FLN", 0);

        routeService.insert(newRoute);
    }

    @Test(expected = BadRequestException.class)
    @DisplayName("should not insert empty route")
    public void shouldNotInsertEmptyRoute() throws IOException {
        RouteDTO newRoute = new RouteDTO("FLN", "", 0);

        routeService.insert(newRoute);
    }
}

package com.bexstech.exam.service;

import com.bexstech.exam.model.Graph;
import com.bexstech.exam.singleton.RouteSingleton;
import com.bexstech.exam.util.ReadFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RouteScannerServiceTest {

    @InjectMocks
    private RouteScannerService routeScannerService;

    private final InputStream systemIn = System.in;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ByteArrayInputStream testIn;

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
        Graph graph = ReadFile.getGraphFromCSV("./resources/routes.csv");
        RouteSingleton.getInstance().updateGraph(graph);
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

    @Test
    @DisplayName("should scan valid input text")
    public void shouldScanValidInputText() {
        final String testString = "GRU-FLN\nexit";
        provideInput(testString);

        routeScannerService.scan();

        assertEquals("please enter the route: \r\n" +
                "best route: GRU - FLN > $25\r\n" +
                "please enter the route: \r\n" +
                "exit\r\n", outContent.toString());
    }

    @Test
    @DisplayName("should scan invalid input text")
    public void shouldScanInvalidInputText() {
        final String testString = "GRU-FL1\nexit";
        provideInput(testString);

        routeScannerService.scan();

        assertEquals("please enter the route: \r\ninvalid input\r\n"
                + "please enter the route: \r\nexit\r\n", outContent.toString());
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}

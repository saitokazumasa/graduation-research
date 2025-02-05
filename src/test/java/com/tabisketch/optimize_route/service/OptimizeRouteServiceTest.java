package com.tabisketch.optimize_route.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class OptimizeRouteServiceTest {
    @Autowired
    private IOptimizeRouteService optimizeRouteService;

    @Test
    public void testExecute() throws IOException {
        this.optimizeRouteService.execute();
    }
}

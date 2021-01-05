package com.bexstech.exam.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("route")
public class RouteController {

    @GetMapping
    public String findRoute() {
        return "OK";
    }

    @PutMapping
    public String insertRoute() {
        return "OK";
    }
}

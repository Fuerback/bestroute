package com.bexstech.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search")
public class SearchRouteController {

    @GetMapping(value = "/best-route")
    public String searchBestRoute() {
        return "OK";
    }
}

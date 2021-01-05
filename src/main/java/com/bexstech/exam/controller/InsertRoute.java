package com.bexstech.exam.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/insert")
public class InsertRoute {

    @PostMapping(value = "/route")
    public String searchBestRoute() {
        return "OK";
    }
}

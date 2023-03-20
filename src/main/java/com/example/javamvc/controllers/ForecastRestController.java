package com.example.javamvc.controllers;

import com.example.javamvc.models.ForecastModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class ForecastRestController {

    @PostMapping(value = "/api/forecast", consumes = "application/json")
    public void index(@RequestBody ForecastModel model) {

    }
}

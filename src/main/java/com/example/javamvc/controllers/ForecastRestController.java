package com.example.javamvc.controllers;

import com.example.javamvc.models.Forecast;
import com.example.javamvc.models.ForecastModel;
import com.example.javamvc.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class ForecastRestController {

    @Autowired
    private ForecastRepository forecastRepository;

    @PostMapping(value = "/api/forecast", consumes = "application/json")
    public void index(@RequestBody ForecastModel model) {
        Forecast entity = new Forecast();
        entity.date = model.date;
        entity.temperature = Double.toString(model.temperature);
        entity.userId = 1;
        entity.city = "Vilnius";
        forecastRepository.save(entity);
    }
}

package com.example.javamvc.controllers;

import com.example.javamvc.models.ForecastModel;
import com.example.javamvc.models.IndexModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ForecastController {

    @GetMapping("/")
    public ModelAndView index() {
        var modelAndView = new ModelAndView("index");

        var indexModel = new IndexModel();

        var cities = new ArrayList<String>();
        cities.add("Vilnius");
        cities.add("Kaunas");
        indexModel.cities = cities;

        var forecats = new ArrayList<ForecastModel>();
        forecats.add(new ForecastModel("2023.03.18 12:00", 2));
        forecats.add(new ForecastModel("2023.03.18 13:00", 2));
        forecats.add(new ForecastModel("2023.03.19 12:00", 10));
        indexModel.forecasts = forecats;

        modelAndView.addObject("IndexModel", indexModel);
        return  modelAndView;
    }

}

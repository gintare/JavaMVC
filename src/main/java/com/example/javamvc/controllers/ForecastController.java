package com.example.javamvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ForecastController {

    @GetMapping("/")
    public ModelAndView index() {
        var modelAndView = new ModelAndView("index");

        var cities = new ArrayList<String>();
        cities.add("Vilnius");
        cities.add("Kaunas");

        modelAndView.addObject("cities", cities);

        return  modelAndView;
    }

}

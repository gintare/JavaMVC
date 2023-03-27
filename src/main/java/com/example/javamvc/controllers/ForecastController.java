package com.example.javamvc.controllers;

import com.example.javamvc.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class ForecastController {

    @GetMapping("/")
    public ModelAndView index(@RequestParam(required = false) String cityCode) throws IOException {
        var modelAndView = new ModelAndView("index");
        if(cityCode == ""){
            cityCode = null;
        }

        var indexModel = new IndexModel();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        indexModel.userName = userName;


        ArrayList<Place> cities = getCities();
        indexModel.cities = cities;

        if (cityCode != null) {
                ArrayList<ForecastModel> forecats = getForecasts(cityCode);
                indexModel.forecasts = forecats;
        }

        indexModel.currentCity = cityCode;


        modelAndView.addObject("IndexModel", indexModel);
        return  modelAndView;
    }

    private static ArrayList<Place> getCities() throws IOException {
        var cities = new ArrayList<Place>();
        String json = loadCitiesDataJson();
        ObjectMapper om = new ObjectMapper();
        Place[] obj = om.readValue(json, Place[].class);
        for(var o : obj){
            var place = new Place();
            place.code = o.code;
            place.name = o.name;
            cities.add(place);
        }

        return cities;
    }

    private static ArrayList<ForecastModel> getForecasts(String city) throws IOException {
        String forecatsJson = loadDataJson("https://api.meteo.lt/v1/places/"+city+"/forecasts/long-term");
        Root root = getObjectFromJson(forecatsJson);

        var forecats = new ArrayList<ForecastModel>();
        for(ForecastTimestamp forecastStamp : root.forecastTimestamps){
            forecats.add(new ForecastModel(forecastStamp.forecastTimeUtc, forecastStamp.airTemperature));
        }
        return forecats;
    }

    private static Root getObjectFromJson(String text) throws JsonProcessingException, JsonMappingException {
        ObjectMapper om = new ObjectMapper();
        Root obj = om.readValue(text, Root.class);
        return obj;
    }

    private static String loadCitiesDataJson() throws IOException {
        return loadDataJson("https://api.meteo.lt/v1/places");
    }

    private static String loadDataJson(String urlStr) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();

        if (responsecode != 200) {
            //logger.log(Integer.toString(responsecode));
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        }

        String text = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            text += scanner.nextLine();
        }
        scanner.close();
        return text;
    }

}

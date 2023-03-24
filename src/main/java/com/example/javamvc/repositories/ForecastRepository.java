package com.example.javamvc.repositories;

import com.example.javamvc.models.Forecast;
import com.example.javamvc.models.ForecastModel;
import org.springframework.data.repository.CrudRepository;

public interface ForecastRepository extends CrudRepository<Forecast, Long> {
}

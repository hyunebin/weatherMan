package com.example.weatherman.DiaryRepository;

import com.example.weatherman.DiaryRepository.entity.DailyWeather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface DailyWeatherRepository extends JpaRepository<DailyWeather, Date> {
    List<DailyWeather> findAllByDate(LocalDate localDate);
    DailyWeather findByDate(LocalDate localDate);
}

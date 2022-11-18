package com.example.weatherman.DiaryRepository.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DailyWeather {

    @Id
    LocalDate date;

    @NotNull
    String weather;
    @NotNull
    String icon;
    @NotNull
    Double temperature;
}



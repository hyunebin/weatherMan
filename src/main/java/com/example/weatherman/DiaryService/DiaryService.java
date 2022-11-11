package com.example.weatherman.DiaryService;


import com.example.weatherman.DTO.DiaryOutPutDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DiaryService {
    boolean createDiary(LocalDate date, String text);


    DiaryOutPutDto.Info getDiary(LocalDate date);

    List<DiaryOutPutDto.Info> getDiaryList(LocalDate startDay, LocalDate endDay);

    DiaryOutPutDto.Info updateDiary(LocalDate date, String text);

    void deleteDiary(LocalDate date);

    void saveDailyWeather();
}

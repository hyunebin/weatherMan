package com.example.weatherman.DiaryService;


import com.example.weatherman.DTO.DiaryOutPutDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DiaryService {
    //다이어리 생성
    boolean createDiary(LocalDate date, String text);

    //다이어리 조회
    DiaryOutPutDto.Info getDiary(LocalDate date);

    //다이어리 전체 리스트 조회
    List<DiaryOutPutDto.Info> getDiaryList(LocalDate startDay, LocalDate endDay);

    //다이어리 수정
    DiaryOutPutDto.Info updateDiary(LocalDate date, String text);

    //다이어리 삭제
    void deleteDiary(LocalDate date);

     void saveDailyWeather();

}

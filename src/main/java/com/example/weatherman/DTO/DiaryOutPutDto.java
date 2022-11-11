package com.example.weatherman.DTO;


import com.example.weatherman.DiaryRepository.entity.Diary;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiaryOutPutDto {
    @Builder
    @Data
    public static class Info{
        private String text;
        private LocalDate localDateTime;


        public static DiaryOutPutDto.Info of(Diary diary){
            return Info.builder()
                    .localDateTime(diary.getDate())
                    .text(diary.getText())
                    .build();
        }

        public static List<DiaryOutPutDto.Info> of(List<Diary> diaries){
            if(diaries != null){
                List<DiaryOutPutDto.Info> list = new ArrayList<>();

                for(Diary x : diaries){
                    list.add(of(x));
                }

                return list;
            }
            return null;
        }
    }
}

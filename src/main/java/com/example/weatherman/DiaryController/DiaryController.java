package com.example.weatherman.DiaryController;

import com.example.weatherman.DTO.DiaryOutPutDto;
import com.example.weatherman.DiaryService.DiaryService;
import com.example.weatherman.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    /**
     * 요청할 날짜, 입력할 내용
     * **/
    @PostMapping("/diary")
    public ResponseEntity<Result<?>> createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date, @RequestBody String text) {
        boolean status = diaryService.createDiary(date, text);
        return ResponseEntity.ok().body(new Result<>(200,status,""));
    }


    @GetMapping("/diary")
    public ResponseEntity<Result<?>> readDiary(@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        DiaryOutPutDto.Info detail = diaryService.getDiary(date);
        return ResponseEntity.ok().body(new Result<>(200,true,detail));
    }

    @GetMapping("/diaries")
    public ResponseEntity<Result<?>> readDiaryList(@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDay){
        List<DiaryOutPutDto.Info> details = diaryService.getDiaryList(startDay,endDay);
        return ResponseEntity.ok().body(new Result<>(200,true,details));
    }

    @PutMapping("/diary")
    @Transactional
    public ResponseEntity<Result<?>> updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestBody String text){
        DiaryOutPutDto.Info detail = diaryService.updateDiary(date, text);
        return ResponseEntity.ok().body(new Result<>(200,true,"수정 완료"));
    }

    @DeleteMapping("/diary")
    @Transactional
    public ResponseEntity<Result<?>> deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
         diaryService.deleteDiary(date);

        return ResponseEntity.ok().body(new Result<>(200,true,"삭제 완료"));
    }
}

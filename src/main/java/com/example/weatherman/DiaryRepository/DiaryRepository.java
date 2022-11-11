package com.example.weatherman.DiaryRepository;

import com.example.weatherman.DiaryRepository.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, Long>{
    Optional<Diary> findByDate(LocalDate date);
    Optional<List<Diary>> findByDateBetween(LocalDate start, LocalDate end);

    void deleteByDate(LocalDate date);
}

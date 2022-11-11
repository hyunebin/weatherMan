package com.example.weatherman.DiaryService;

import com.example.weatherman.DTO.DiaryOutPutDto;
import com.example.weatherman.DiaryRepository.DailyWeatherRepository;
import com.example.weatherman.DiaryRepository.DiaryRepository;
import com.example.weatherman.DiaryRepository.entity.DailyWeather;
import com.example.weatherman.DiaryRepository.entity.Diary;
import com.example.weatherman.Exception.DiaryException.DiaryException;
import com.example.weatherman.Exception.DiaryException.code.DiaryErrorCode;
import com.example.weatherman.WeatherManApplication;
import jdk.vm.ci.meta.Local;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherManApplication.class);

    private final DiaryRepository diaryRepository;
    private final DailyWeatherRepository dailyWeatherRepository;
    @Value("${openWeatherMap.key}")
    private String key;

    @Override
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteByDate(date);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 1 * * *") // 초 분 시 일 월 0 초 0 분 1시에 매일 매달 매일 1시마다 저장
    public void saveDailyWeather() {
        dailyWeatherRepository.save(getDailyWeather());
    }

    @Override
    public DiaryOutPutDto.Info updateDiary(LocalDate date, String text) {
        Optional<Diary> optionalDiary = diaryRepository.findByDate(date);
        Diary diary = optionalDiary.orElseThrow(()-> new DiaryException(DiaryErrorCode.DIARY_NOT_EXISTS));

        diary.setText(text);
        return DiaryOutPutDto.Info.of(diary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaryOutPutDto.Info> getDiaryList(LocalDate startDay, LocalDate endDay) {
        Optional<List<Diary>> optionalDiaryList = diaryRepository.findByDateBetween(startDay,endDay);
        List<Diary> diary = optionalDiaryList.orElseThrow(()-> new DiaryException(DiaryErrorCode.DIARY_NOT_EXISTS));
        return DiaryOutPutDto.Info.of(diary);
    }

    @Override
    @Transactional(readOnly = true)
    public DiaryOutPutDto.Info getDiary(LocalDate date) {
        Optional<Diary> optionalDiary = diaryRepository.findByDate(date);
        Diary diary = optionalDiary.orElseThrow(()-> new DiaryException(DiaryErrorCode.DIARY_NOT_EXISTS));
        return DiaryOutPutDto.Info.of(diary);
    }

    public DailyWeather getWeatherApi(LocalDate date){
        DailyWeather dailyWeather = new DailyWeather();
        String weather = getWeatherString();
        Map<String, Object> parseWeather = parseWeather(weather);

        dailyWeather.setDate(date);
        dailyWeather.setIcon(parseWeather.get("icon").toString());
        dailyWeather.setTemperature((Double)parseWeather.get("temp"));
        dailyWeather.setWeather(parseWeather.get("main").toString());

        return dailyWeather;
    }

    @Override
    public boolean createDiary(LocalDate date, String text) {
        logger.info("started to created diary");
        DailyWeather dailyWeather;
        List<DailyWeather> dailyWeatherList = dailyWeatherRepository.findAllByDate(date);

        if(dailyWeatherList.size() == 0){
           dailyWeather = getWeatherApi(date);
        } else {
            dailyWeather = dailyWeatherRepository.findByDate(date);
        }

        Diary diary = diaryRepository.save(
            Diary.builder()
                    .date(dailyWeather.getDate())
                    .text(text)
                    .weather(dailyWeather.getWeather())
                    .icon(dailyWeather.getIcon())
                    .temperature(dailyWeather.getTemperature())
                    .build());

        logger.info("end to created diary");
        return true;
    }

    private DailyWeather getDailyWeather(){
        String weather = getWeatherString();
        Map<String, Object> parseWeather = parseWeather(weather);

        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setDate(LocalDate.now());
        dailyWeather.setWeather(parseWeather.get("main").toString());
        dailyWeather.setIcon(parseWeather.get("icon").toString());
        dailyWeather.setTemperature((double)parseWeather.get("temp"));

        return dailyWeather;
    }

    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        JSONArray weatherData= (JSONArray) jsonObject.get("weather");
        JSONObject weather = (JSONObject) weatherData.get(0);

        resultMap.put("temp",mainData.get("temp"));
        resultMap.put("main", weather.get("main"));
        resultMap.put("icon", weather.get("icon"));

        return resultMap;
    }

    private String getWeatherString() {
        String urlAPI = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + key;


        try {
            URL url = new URL(urlAPI);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode(); // 상태 코드
            BufferedReader bufferedReader;

            if (status == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // 성공시
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream())); // 실패시
            }


            StringBuilder response = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                response.append(input);
            }

            bufferedReader.close();
            return response.toString();
        } catch (Exception e) {
            return "";
        }
    }
}

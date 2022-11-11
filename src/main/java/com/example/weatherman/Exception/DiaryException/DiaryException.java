package com.example.weatherman.Exception.DiaryException;

import com.example.weatherman.Exception.DiaryException.code.DiaryErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryException extends RuntimeException {

    private DiaryErrorCode errorCode;
    private String errorMessage;

    public DiaryException(DiaryErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}

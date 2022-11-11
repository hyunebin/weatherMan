package com.example.weatherman.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Result<T> {
    private Integer status;
    private boolean success;
    private T data;

}

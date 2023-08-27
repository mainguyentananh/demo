package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityV2<T> {
    private int status;
    private String message;
    private T data;

    public ResponseEntityV2(T data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = data;
    }
}

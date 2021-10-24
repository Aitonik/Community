package com.community.community.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Обертка для ответа на фронт
 *
 */
@Data
@Builder
public class ApiCallResult<T> implements Serializable {

    T payload;
    HttpStatus status;
    String info;

}



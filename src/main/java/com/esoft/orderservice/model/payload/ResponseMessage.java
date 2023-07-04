package com.esoft.orderservice.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> implements Serializable {
    private String code;
    private String description;
    private Boolean success;
    private T data;
}

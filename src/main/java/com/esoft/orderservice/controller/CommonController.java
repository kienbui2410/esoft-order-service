package com.esoft.orderservice.controller;

import com.esoft.orderservice.common.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.esoft.orderservice.model.payload.ResponseMessage;

public abstract class CommonController {

    protected <T> ResponseEntity<?> toSuccessResult(T data, String description) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setCode(AppConstants.API_RESPONSE.RETURN_CODE_SUCCESS);
        message.setSuccess(AppConstants.API_RESPONSE.RESPONSE_STATUS_TRUE);
        message.setData(data);
        message.setDescription(description);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<?> toSuccessResult(T data) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setCode(AppConstants.API_RESPONSE.RETURN_CODE_SUCCESS);
        message.setSuccess(AppConstants.API_RESPONSE.RESPONSE_STATUS_TRUE);
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<?> toExceptionResult(String errorMessage, String code) {
        ResponseMessage<T> message = new ResponseMessage<>();

        message.setSuccess(AppConstants.API_RESPONSE.RESPONSE_STATUS_FALSE);
        message.setCode(code);
        message.setDescription(errorMessage);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

package com.example.tallerredes.dtos;

public class ResponsePutLocationDto {
    public boolean success;
    public String message;


    public ResponsePutLocationDto() {
    }

    public ResponsePutLocationDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

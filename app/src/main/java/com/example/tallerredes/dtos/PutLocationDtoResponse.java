package com.example.tallerredes.dtos;

public class PutLocationDtoResponse {
    public boolean success;
    public String message;


    public PutLocationDtoResponse() {
    }

    public PutLocationDtoResponse(boolean success, String message) {
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

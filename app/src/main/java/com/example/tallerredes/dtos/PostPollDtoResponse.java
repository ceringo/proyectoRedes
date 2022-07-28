package com.example.tallerredes.dtos;

public class PostPollDtoResponse {

    public boolean success;
    public String message;
    public int pollIdCreated;


    public PostPollDtoResponse() {
    }

    public PostPollDtoResponse(boolean success, String message, int pollIdCreated) {
        this.success = success;
        this.message = message;
        this.pollIdCreated = pollIdCreated;
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

    public int getPollIdCreated() {
        return pollIdCreated;
    }

    public void setPollIdCreated(int pollIdCreated) {
        this.pollIdCreated = pollIdCreated;
    }
}

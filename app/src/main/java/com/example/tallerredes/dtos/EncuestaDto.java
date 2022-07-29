package com.example.tallerredes.dtos;


import java.util.List;

public class EncuestaDto {

    public String Fullname;
    public String Datebirth;
    public String Address;
    public String PhoneNumber;
    public List<QuestionDto> Questions;
    public String AudioEncode;
    public float Latitude;
    public float Longitude;


    public int UserId;


    public String getFullname() {
        return Fullname;
    }

    public EncuestaDto setFullname(String fullname) {
        Fullname = fullname;
        return this;
    }

    public String getDatebirth() {
        return Datebirth;
    }

    public EncuestaDto setDatebirth(String datebirth) {
        Datebirth = datebirth;
        return this;
    }

    public String getAddress() {
        return Address;
    }

    public EncuestaDto setAddress(String address) {
        Address = address;
        return this;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public EncuestaDto setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        return this;
    }

    public List<QuestionDto> getQuestions() {
        return Questions;
    }

    public EncuestaDto setQuestions(List<QuestionDto> questions) {
        Questions = questions;
        return this;
    }

    public String getAudioEncode() {
        return AudioEncode;
    }

    public EncuestaDto setAudioEncode(String audioEncode) {
        AudioEncode = audioEncode;
        return this;
    }

    public int getUserId() {
        return UserId;
    }

    public EncuestaDto setUserId(int userId) {
        UserId = userId;
        return this;
    }

    public float getLatitude() {
        return Latitude;
    }

    public EncuestaDto setLatitude(float latitude) {
        Latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return Longitude;
    }

    public EncuestaDto setLongitude(float longitude) {
        Longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return
                " NOMBRE COMPLETO:" + '\t' + Fullname + '\n' +
                        " FECHA DE NAC.: " + '\t' + Datebirth + '\n' +
                        " DIRECION:" + '\t' + Address + '\n' +
                        " TELEFONO:" + '\t' + PhoneNumber + '\n' +
                        " PREGUNTAS: " + '\n' + Questions + '\n';
    }
}

package com.example.tallerredes.apis;

import com.example.tallerredes.dtos.EncuestaDto;
import com.example.tallerredes.dtos.ResponsePostPollDto;
import com.example.tallerredes.dtos.ResponsePutLocationDto;
import com.example.tallerredes.dtos.ResponseSignInDto;
import com.example.tallerredes.dtos.SignInDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndpointsPolls {

    @POST("session/signin/{userType}")
    Call<ResponseSignInDto> signIn(@Body SignInDto auth, @Path("userType") int userType);

    @POST("poll")
    Call<ResponsePostPollDto> postPoll(@Body EncuestaDto encuestaDto);


    @PUT("pollster/{pollsterId}")
    Call<ResponsePutLocationDto> putLocation(@Path("pollsterId") int pollsterId, @Query("Latitude") float latitude, @Query("Longitude") float longitude);

}

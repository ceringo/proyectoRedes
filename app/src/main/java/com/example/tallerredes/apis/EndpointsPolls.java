package com.example.tallerredes.apis;

import com.example.tallerredes.dtos.EncuestaDto;
import com.example.tallerredes.dtos.PostPollDtoResponse;
import com.example.tallerredes.dtos.PutLocationDtoResponse;
import com.example.tallerredes.dtos.SignInDtoResponse;
import com.example.tallerredes.dtos.SignInDto;
import com.example.tallerredes.dtos.UploadFileResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndpointsPolls {

    @POST("session/signin/{userType}")
    Call<SignInDtoResponse> signIn(@Body SignInDto auth, @Path("userType") int userType);

    @POST("poll")
    Call<PostPollDtoResponse> postPoll(@Body EncuestaDto encuestaDto);

    @GET("poll/{userId}/user")
    Call<List<EncuestaDto>> getAllPollsByUserId(@Path("userId") int userId);

    @PUT("pollster/{pollsterId}")
    Call<PutLocationDtoResponse> putLocation(@Path("pollsterId") int pollsterId, @Query("Latitude") float latitude, @Query("Longitude") float longitude);

    @POST("poll/{pollId}/upload")
    Call<UploadFileResponse> postAudioFile(@Path("pollId") int pollId, @Body RequestBody file);

    @POST("poll/{pollId}/upload")
    @Multipart
    Call<UploadFileResponse> uploadFilee(@Path("pollId") int pollId, @Part MultipartBody.Part file);
}

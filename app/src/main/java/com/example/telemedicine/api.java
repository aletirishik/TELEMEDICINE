package com.example.telemedicine;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface api {
    @Headers({"Content-Type: application/json","Authorization: Bearer svm"})
    @POST("{id}")
    Call<ResponseBody> call_api(@Path("id") String id, @Body RequestBody params);
}

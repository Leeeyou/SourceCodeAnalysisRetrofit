package com.leeeyou.source.code.analysis.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GankService {
    @GET("xiandu/categories")
    Call<ResponseCategory> categories();
}
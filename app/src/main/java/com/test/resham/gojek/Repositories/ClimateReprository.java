package com.test.resham.gojek.Repositories;

import com.test.resham.gojek.Model.APIXUModel;
import com.test.resham.gojek.Utils.AppConstatnts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fussion on 8/20/18.
 */

public interface ClimateReprository {
    @GET("current.json?key=" + AppConstatnts.APIXU_KEY)
    Call<APIXUModel> getCurrentInfo(@Query("q") String location);

    @GET("forecast.json?key=" + AppConstatnts.APIXU_KEY)
    Call<APIXUModel> getForecastInfo(@Query("q") String location, @Query("days") int days);
}

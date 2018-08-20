package com.test.resham.gojek.Controllers;

import android.content.Context;

import com.test.resham.gojek.Model.APIXUModel;
import com.test.resham.gojek.Model.Forecastday;
import com.test.resham.gojek.Repositories.ClimateReprository;
import com.test.resham.gojek.Services.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fussion on 8/20/18.
 */

public class HomeController {
    private Context mContext;
    private APIResponse mListener;

    public interface APIResponse {
        void ClimateResponseCallBack(APIXUModel response);

        void ForecastResponseCallBack(Forecastday[] response);

        void ShowErrorResponse();
    }

    public HomeController(Context mContext) {
        this.mContext = mContext;
        this.mListener = (APIResponse) mContext;
    }

    public void getClimateInfo(String location) {
        ClimateReprository reprository = RetrofitClientInstance.getRetrofitInstance().create(ClimateReprository.class);
        Call<APIXUModel> call = reprository.getCurrentInfo(location);
        call.enqueue(new Callback<APIXUModel>() {
            @Override
            public void onResponse(Call<APIXUModel> call, Response<APIXUModel> response) {
                if (response != null) {
                    mListener.ClimateResponseCallBack(response.body());
                } else
                    mListener.ShowErrorResponse();

            }

            @Override
            public void onFailure(Call<APIXUModel> call, Throwable t) {
                mListener.ShowErrorResponse();
            }
        });
    }

    public void getForecastInfo(String location) {
        ClimateReprository reprository = RetrofitClientInstance.getRetrofitInstance().create(ClimateReprository.class);
        Call<APIXUModel> call = reprository.getForecastInfo(location, 4);
        call.enqueue(new Callback<APIXUModel>() {
            @Override
            public void onResponse(Call<APIXUModel> call, Response<APIXUModel> response) {
                if (response != null) {
                    Forecastday[] forecastdays = response.body().getForecast().getForecastday();
                    mListener.ForecastResponseCallBack(forecastdays);
                } else
                    mListener.ShowErrorResponse();

            }

            @Override
            public void onFailure(Call<APIXUModel> call, Throwable t) {
                mListener.ShowErrorResponse();
            }
        });
    }
}

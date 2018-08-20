package com.test.resham.gojek.Model;

/**
 * Created by fussion on 8/20/18.
 */

public class Forecast {
    private Forecastday[] forecastday;

    public Forecastday[] getForecastday ()
    {
        return forecastday;
    }

    public void setForecastday (Forecastday[] forecastday)
    {
        this.forecastday = forecastday;
    }
}

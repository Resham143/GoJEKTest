package com.test.resham.gojek.Model;

/**
 * Created by fussion on 8/20/18.
 */

public class APIXUModel {
    private Location location;
    private Forecast forecast;
    private Current current;
    public Forecast getForecast ()
    {
        return forecast;
    }

    public void setForecast (Forecast forecast)
    {
        this.forecast = forecast;
    }
    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public Current getCurrent ()
    {
        return current;
    }

    public void setCurrent (Current current)
    {
        this.current = current;
    }
}

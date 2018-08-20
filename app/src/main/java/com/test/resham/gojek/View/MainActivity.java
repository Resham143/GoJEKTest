package com.test.resham.gojek.View;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.test.resham.gojek.Controllers.HomeController;
import com.test.resham.gojek.Model.APIXUModel;
import com.test.resham.gojek.Model.Forecastday;
import com.test.resham.gojek.R;
import com.test.resham.gojek.View.Adapter.ClimateAdapter;

public class MainActivity extends Activity implements  HomeController.APIResponse {
    int LOCATION_PERMISSIONS_REQUEST = 111;
    Geocoder geocoder;
    ProgressBar img_loading;
    TextView lbl_city, lbl_temp, lbl_error;
    ListView lst_forecast;
    private static final float ROTATE_FROM = 30.0f;
    private static final float ROTATE_TO = 360.0f;
    PopupWindow popupWindow;
    View errorView;
    Button btn_retry;
    Typeface roboto_black, roboto_thin;
    HomeController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        checkLocationPermission();
        roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        roboto_thin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        ;
        lbl_city = (TextView) findViewById(R.id.lbl_city);
        lbl_city.setTypeface(roboto_thin);
        lbl_temp = (TextView) findViewById(R.id.lbl_temp);
        lbl_temp.setTypeface(roboto_black);
        img_loading = (ProgressBar) findViewById(R.id.loading_indicator);
        lst_forecast = (ListView) findViewById(R.id.lst_forecast);
        controller = new HomeController(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSIONS_REQUEST);

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Location mLastLocation = task.getResult();
                                if (mLastLocation != null) {
                                    String location = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();
                                    controller.getClimateInfo(location);
                                    controller.getForecastInfo(location);
                                }
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Please enable the location permission.", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void ClimateResponseCallBack(APIXUModel response) {
        img_loading.setVisibility(View.GONE);
        lbl_city.setText(response.getLocation().getRegion());
        lbl_temp.setText(response.getCurrent().getTemp_c() + (char) 0x00B0);
    }

    @Override
    public void ForecastResponseCallBack(Forecastday[] response) {
        lst_forecast.setAdapter(new ClimateAdapter(MainActivity.this, response));
    }

    @Override
    public void ShowErrorResponse() {
        if (popupWindow == null || errorView == null) {
            errorView = getLayoutInflater().inflate(R.layout.error_layout, null, false);
            popupWindow = new PopupWindow(errorView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            btn_retry = errorView.findViewById(R.id.btn_retry);
            lbl_error = (TextView) findViewById(R.id.lbl_error);
            lbl_error.setTypeface(roboto_thin);
        }
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                getLocation();
            }
        });
        popupWindow.showAtLocation(errorView, Gravity.CENTER, 0, 0);
    }
}

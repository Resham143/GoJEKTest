package com.test.resham.gojek.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.resham.gojek.Model.Forecastday;
import com.test.resham.gojek.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fussion on 8/20/18.
 */

public class ClimateAdapter extends ArrayAdapter<Forecastday> {
    Typeface roboto_regular ;
    public ClimateAdapter(Context context, Forecastday[] climates)
    {
        super(context, 0, climates);
        roboto_regular =  Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Forecastday dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.forecast_adapter, parent, false);
            viewHolder.lbl_days = (TextView) convertView.findViewById(R.id.lbl_days);
            viewHolder.lbl_temp = (TextView) convertView.findViewById(R.id.lbl_temp);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.lbl_days.setText( converDateToDay(dataModel.getDate()));
        viewHolder.lbl_temp.setText(dataModel.getDay().getAvgtemp_c() +" C");
        viewHolder.lbl_days.setTypeface(roboto_regular);
        viewHolder.lbl_temp.setTypeface(roboto_regular);
        return convertView;
    }
    private static class ViewHolder {
        TextView lbl_days;
        TextView lbl_temp;

    }
    private  String converDateToDay(String date) {
        String input_date = date;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 =new Date();
        try {
             dt1 = format1.parse(input_date);
        }
        catch (Exception ex)
        {
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        return  finalDay;
    }
}

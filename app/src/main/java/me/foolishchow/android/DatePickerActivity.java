package me.foolishchow.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import me.foolishchow.android.databinding.ActivityDatePickerBinding;
import me.foolishchow.android.datepicker.OnDatePickerSelectListener;
import me.foolishchow.android.datepicker.WheelTime;
import me.foolishchow.android.datepicker.options.DatePickerOption;

public class DatePickerActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    private DateFormat FORMAT = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatePickerBinding viewBind = ActivityDatePickerBinding.inflate(getLayoutInflater());

        setContentView(viewBind.getRoot());

        DatePickerOption option = new DatePickerOption();
        option.setRangeStart(2020,12,20,0,30,0);
        //option.setRangeEnd(2003,6,20);
        option.setSelected(2020,12,20,0,31,0);
        //option.setSelected(2020,12,20,0,31,0);
        option.setStyle(WheelTime.STYLE_DATE_HOUR_MINUTE);


        final WheelTime wheelTime = new WheelTime();
        wheelTime.setWheels(
                viewBind.year, viewBind.month, viewBind.day,
                viewBind.hour, viewBind.minute, viewBind.second);
        wheelTime.setRangDate(option.getRangeStart(),option.getRangeEnd());
        wheelTime.setStyle(option.getStyle());

        wheelTime.setDatePickerSelectListener(new OnDatePickerSelectListener() {
            @Override
            public void onDatePickerSelect() {
                Log.e("onTimeSelectChanged", FORMAT.format(wheelTime.getTime()));
            }
        });
        wheelTime.setSelected(option.getSelected());
    }
}
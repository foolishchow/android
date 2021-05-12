package me.foolishchow.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import me.foolishchow.android.databinding.ActivityDatePickerBinding;
import me.foolishchow.android.datepicker.DateStyle;
import me.foolishchow.android.datepicker.LunarWheelTime;
import me.foolishchow.android.datepicker.OnDatePickerSelectListener;
import me.foolishchow.android.datepicker.WheelTime;
import me.foolishchow.android.datepicker.lunar.Lunar;
import me.foolishchow.android.datepicker.options.DatePickerOption;

public class DatePickerActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    private DateFormat FORMAT = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatePickerBinding viewBind = ActivityDatePickerBinding.inflate(getLayoutInflater());

        setContentView(viewBind.getRoot());


        initSolar(viewBind);

        initLunar(viewBind);
    }

    private void initLunar(ActivityDatePickerBinding viewBind){
        final LunarWheelTime wheelTime = new LunarWheelTime(
                viewBind.year1, viewBind.month1, viewBind.day1,
                viewBind.hour1, viewBind.minute1
        );

        DatePickerOption option = new DatePickerOption();
        option.setRangeStart(1990,1,1);
        option.setRangeEnd(2100,12,31);
        option.setSelected(2003,6,20);

        option.setStyle(DateStyle.STYLE_DATE_HOUR_MINUTE);
        option.setOnDatePickerSelectListener(new OnDatePickerSelectListener() {
            @Override
            public void onDatePickerSelect() {
                Lunar time = wheelTime.getTime();
                Log.e("onTimeSelectChanged", time.toString());
            }
        });
        wheelTime.setOption(option);

    }


    private void initSolar(ActivityDatePickerBinding viewBind) {
        final WheelTime wheelTime = new WheelTime(
                viewBind.year, viewBind.month, viewBind.day,
                viewBind.hour, viewBind.minute, viewBind.second
        );
        DatePickerOption option = new DatePickerOption();
        option.setRangeStart(2020,12,20,0,30,0);
        //option.setRangeEnd(2003,6,20);
        option.setSelected(2020,12,20,0,31,0);
        //option.setSelected(2020,12,20,0,31,0);
        option.setStyle(DateStyle.STYLE_DATE_HOUR_MINUTE);
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
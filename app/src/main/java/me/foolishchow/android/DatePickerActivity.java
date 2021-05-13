package me.foolishchow.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.foolishchow.android.databinding.ActivityDatePickerBinding;
import me.foolishchow.android.datepicker.DateStyle;
import me.foolishchow.android.datepicker.LunarWheelTime;
import me.foolishchow.android.datepicker.OnDatePickerSelectListener;
import me.foolishchow.android.datepicker.SolarLunarWheelTime;
import me.foolishchow.android.datepicker.WheelTime;
import me.foolishchow.android.datepicker.lunar.Lunar;
import me.foolishchow.android.datepicker.lunar.LunarSolarConverter;
import me.foolishchow.android.datepicker.options.DatePickerOption;
import me.foolishchow.android.datepicker.options.LunarDatePickerOption;

public class DatePickerActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    private final DateFormat FORMAT = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatePickerBinding viewBind = ActivityDatePickerBinding.inflate(getLayoutInflater());

        setContentView(viewBind.getRoot());
        initSolar(viewBind);
        initLunar(viewBind);
    }

    private void initLunar(ActivityDatePickerBinding viewBind) {
        final SolarLunarWheelTime wheelTime = new SolarLunarWheelTime(
                viewBind.year1, viewBind.month1, viewBind.day1,
                viewBind.hour1, viewBind.minute1
        );

        DatePickerOption option = new DatePickerOption();
        option.setRangeStart(1990, 1, 1);
        option.setRangeEnd(2100, 12, 31);
        option.setSelected(2003, 6, 20);

        option.setStyle(DateStyle.STYLE_DATE_HOUR_MINUTE);
        option.setOnDatePickerSelectListener(new OnDatePickerSelectListener() {
            @Override
            public void onDatePickerSelect() {
                boolean lunarMode = wheelTime.getLunarMode();
                if (lunarMode) {
                    viewBind.mixValue.setText(FORMAT.format(wheelTime.getTime()));
                } else {
                    Lunar lunar = Lunar.from(wheelTime.getTime());
                    viewBind.mixValue.setText(String.format("%d%s年%s月%s",
                            lunar.lunarYear,
                            lunar.getYearName(),
                            lunar.getMonthName(),
                            lunar.getDayName()
                    ));
                }
                Log.e("onTimeSelectChanged", FORMAT.format(wheelTime.getTime()));
            }
        });
        wheelTime.setOption(option);
        viewBind.solarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wheelTime.setLunarMode(false);
                } else {
                    wheelTime.setLunarMode(true);
                }
            }
        });
    }


    private void initSolar(ActivityDatePickerBinding viewBind) {
        final LunarWheelTime wheelTime = new LunarWheelTime(
                viewBind.year, viewBind.month, viewBind.day,
                viewBind.hour, viewBind.minute, viewBind.second
        );
        LunarDatePickerOption option = new LunarDatePickerOption();
        Lunar start = Lunar.from(2010, 12, 20, 0, 30);
        Lunar end = Lunar.from(2050, 6, 20);
        option.setRangeStart(start.toDate());
        option.setRangeEnd(end.toDate());
        option.setShowLeapMonth(false);
        option.setStyle(DateStyle.STYLE_DATE_HOUR_MINUTE);
        option.setOnDatePickerSelectListener(new OnDatePickerSelectListener() {
            @Override
            public void onDatePickerSelect() {
                Date date = wheelTime.getTime().toDate();
                Log.e("onTimeSelectChanged", FORMAT.format(date));
            }
        });
        wheelTime.setOption(option);
    }
}
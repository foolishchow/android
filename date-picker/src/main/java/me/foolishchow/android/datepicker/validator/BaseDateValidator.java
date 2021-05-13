package me.foolishchow.android.datepicker.validator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

import me.foolishchow.android.datepicker.utils.Utils;

public abstract class BaseDateValidator {

    protected final int[] mRangeStart = new int[]{1990, 1, 1, 0, 0, 0};
    protected final int[] mRangeEnd = new int[]{2100, 12, 31, 23, 59, 59};
    protected final int[] mSelected = new int[]{1990, 1, 1, 0, 0, 0};
    protected final int[][] mRangeCurrent = new int[][]{
            {1990, 2100}, {1, 12}, {1, 31}, {0, 23}, {0, 59}, {0, 59}
    };

    private void updateDateArray(int[] array, @NonNull Calendar calendar) {
        array[0] = calendar.get(Calendar.YEAR);
        array[1] = calendar.get(Calendar.MONTH) + 1;
        array[2] = calendar.get(Calendar.DAY_OF_MONTH);
        array[3] = calendar.get(Calendar.HOUR_OF_DAY);
        array[4] = calendar.get(Calendar.MINUTE);
        array[5] = calendar.get(Calendar.SECOND);
    }

    public void setRangDate(@NonNull Calendar startDate, @NonNull Calendar endDate) {
        updateDateArray(mRangeStart, startDate);
        updateDateArray(mRangeEnd, endDate);
        mRangeCurrent[0][0] = mRangeStart[0];
        mRangeCurrent[0][1] = mRangeEnd[0];
    }


    public void setSelected(
            int year, int month, int dayOfMonth,
            int hourOfDay, int minute, int second
    ) {
        mSelected[0] = year;
        mSelected[1] = month;
        mSelected[2] = dayOfMonth;
        mSelected[3] = hourOfDay;
        mSelected[4] = minute;
        mSelected[5] = second;
        Validate();
    }

    abstract void Validate();

    @Nullable
    protected ValidatedListener mValidatedListener;

    public void setValidatedListener(ValidatedListener listener) {
        this.mValidatedListener = listener;
    }

    public Date getTime() {
        //Log.e("getTime",
        //        String.format("%02d-%02d-%02d %02d:%02d:%02d",
        //                mSelected[0], mSelected[1], mSelected[2],
        //                mSelected[3], mSelected[4], mSelected[5]
        //        )
        //);
        return Utils.asDate(
                mSelected[0], mSelected[1], mSelected[2],
                mSelected[3], mSelected[4], mSelected[5]);
    }
}

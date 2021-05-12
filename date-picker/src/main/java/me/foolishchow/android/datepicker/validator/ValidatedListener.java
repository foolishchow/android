package me.foolishchow.android.datepicker.validator;

import androidx.annotation.NonNull;

public interface ValidatedListener {
    void onDateTimeValidated(
            @NonNull ValidateResult year, @NonNull ValidateResult month, @NonNull ValidateResult dayOfMonth,
            @NonNull ValidateResult hourOfDay, @NonNull ValidateResult minute, @NonNull ValidateResult second
    );
}

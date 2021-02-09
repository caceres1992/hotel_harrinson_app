package com.example.harrinsonhotelapp.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public interface CalendarListener extends com.archit.calendardaterangepicker.customviews.@NotNull CalendarListener {

    void onFirstDateSelected(Calendar startDate);
    void onDateRangeSelected(Calendar startDate, Calendar endDate);
}

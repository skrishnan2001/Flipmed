package com.microservices.app.utils;

import com.microservices.app.models.TimeSlot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static LocalTime convertStringTimeToLocalTime(String time) {
        String[] splitTime = time.split(":");
        return LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
    }

    public static TimeSlot createThirtyMinutesTimeSlotWithStartTime(String startTime) {
        LocalTime startTimeLocalTime = convertStringTimeToLocalTime(startTime);
        LocalTime endTimeLocalTime = startTimeLocalTime.plusMinutes(30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return new TimeSlot(startTime, endTimeLocalTime.format(formatter));
    }

}

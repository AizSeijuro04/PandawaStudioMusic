package com.example.pandawastudiomusic.Common;

import com.example.pandawastudiomusic.Model.BookingInformation;
import com.example.pandawastudiomusic.Model.Studio;
import com.example.pandawastudiomusic.Model.Studios;
import com.example.pandawastudiomusic.Model.TimeSlot;
import com.example.pandawastudiomusic.Model.User;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

    public static final String IS_LOGIN = "IsLogin";
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_STUDIO_STORE = "STUDIO_SAVE";
    public static final String KEY_STUDIOS_LOAD_DONE = "STUDIOS_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_STUDIOS_SELECTED = "STUDIOS_SELECTED";
    public static final int TIME_SLOT_TOTAL = 16;
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static final String EVENT_URI_CACHE = "URI_EVENT_SAVE";
    public static User currentUser;
    public static Studio currentStudio;
    public static int step = 0; // init first step is 0
    public static String city = "";
    public static Studios currentStudios;
    public static int currentTimeSlot=-1;
    public static Calendar bookingDate = Calendar.getInstance();
    public static SimpleDateFormat simpleFormatDate = new SimpleDateFormat("dd_MM_yyyy"); //only use when need format key
    public static BookingInformation currentBooking;
    public static String currentBookingId = "";


    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "08:00 - 09:00";
            case 1:
                return "09:00 - 10:00";
            case 2:
                return "10:00 - 11:00";
            case 3:
                return "11:00 - 12:00";
            case 4:
                return "12:00 - 13:00";
            case 5:
                return "13:00 - 14:00";
            case 6:
                return "14:00 - 15:00";
            case 7:
                return "15:00 - 16:00";
            case 8:
                return "16:00 - 17:00";
            case 9:
                return "17:00 - 18:00";
            case 10:
                return "18:00 - 19:00";
            case 11:
                return "19:00 - 20:00";
            case 12:
                return "20:00 - 21:00";
            case 13:
                return "21:00 - 22:00";
            case 14:
                return "22:00 - 23:00";
            case 15:
                return "23:00 - 00:00";
            default:
                return "Tutup";
        }
    }

    public static String convertTimeStampToStringKey(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        return simpleDateFormat.format(date);
    }

    public static String formatShoppingItemName(String name) {
        return name.length() > 13 ? new StringBuilder(name.substring(0,10)).append("...").toString():name;
    }
}

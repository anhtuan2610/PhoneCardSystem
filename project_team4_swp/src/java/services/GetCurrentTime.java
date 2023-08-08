/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author phamtung
 */
public class GetCurrentTime {

    public static long getCurrentTime() {
        Date now = new Date();

        // Định dạng thời gian thành "yyyyMMddHHmm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String formattedDate = dateFormat.format(now);

        // Chuyển đổi thành kiểu long
        long timestamp = Long.parseLong(formattedDate);

        return timestamp;

    }

    public static String longToDate(String timestamp) {
        String year = timestamp.substring(0, 4);
        String month = timestamp.substring(4, 6);
        String day = timestamp.substring(6, 8);
        String hour = timestamp.substring(8, 10);
        String minute = timestamp.substring(10, 12);

        String formattedDate = day + " - " + month + " - " + year + " - " + hour + ":" + minute;

        return formattedDate;

    }

    public static long subtractDays(long timestamp, int days) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            // Chuyển đổi timestamp thành đối tượng Date
            Date date = dateFormat.parse(String.valueOf(timestamp));

            // Sử dụng lớp Calendar để trừ đi số ngày
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -days);

            // Chuyển đổi lại thành giá trị long
            String formattedDate = dateFormat.format(calendar.getTime());
            return Long.parseLong(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long subtractMonths(long timestamp, int months) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            // Chuyển đổi timestamp thành đối tượng Date
            Date date = dateFormat.parse(String.valueOf(timestamp));

            // Sử dụng lớp Calendar để trừ đi số tháng
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -months);

            // Chuyển đổi lại thành giá trị long
            String formattedDate = dateFormat.format(calendar.getTime());
            return Long.parseLong(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String formatExpiryDate(String timestamp) {
        String year = timestamp.substring(0, 4);
        String month = timestamp.substring(4, 6);
        String day = timestamp.substring(6, 8);

        String formattedDate = day + " - " + month + " - " + year;

        return formattedDate;

    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
//        long timestamp = getCurrentTime();
////        long timestamp7DaysAgo = subtractDays(timestamp, 30);
////        
////        long timestamp6MonthsAgo = subtractMonths(timestamp, 6);
//        System.out.println(timestamp);
//
//        boolean flag = isInteger("03");
//
//        System.out.println(flag);
//        System.out.println(Integer.parseInt("03"));

            

    }
}

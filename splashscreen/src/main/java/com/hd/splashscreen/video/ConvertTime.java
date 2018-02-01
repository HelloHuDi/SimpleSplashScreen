package com.hd.splashscreen.video;

/**
 * Created by hd on 2018/1/21 .
 * 把毫秒转成时间hh:mi:ss.xxx
 */
public class ConvertTime {

    public static String secToTime(int time) {
        String timeStr ;
        int hour, minute, second;
        if (time <= 0)
            return "00:00:00";
        else {
            second = time / 1000;
            minute = second / 60;
            if (second < 60) {
                timeStr = "00:00:" + unitFormat(second);
            } else if (minute < 60) {
                second = second % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = second - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 时分秒的格式转换
     */
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
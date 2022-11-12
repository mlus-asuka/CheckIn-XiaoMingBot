package vip.fubuki.util;

import vip.fubuki.CheckInPlugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalculateCode {
    public static Result TimeCalculate(long UserQQ){
        Calendar calendar = new GregorianCalendar();
        Result result=new Result();
        long NowTime = calendar.getTimeInMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(simpleDateFormat.parse(GetLastTime(UserQQ)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long TargetTime = calendar.getTimeInMillis();
        long time = NowTime-TargetTime;
        long day = time/(1000*3600*24),hour = time/(1000*60*60),minute = time/(1000*60),second = time/(1000);
        second -= minute*60;
        minute -= hour*60;
        hour -= day*24;
        if(day>=1){
            result.setChecked(true);
        }
        else {
            if (hour == 23) {
                if(minute==59){
                    result.setMessage("签到失败,距离下次签到时间还有" + (59 - second) + "秒");
                }else {
                    result.setMessage("签到失败,距离下次签到时间还有" + (59 - minute) + "分" + (59 - second) + "秒");
                }
            }
            else {
                result.setMessage("签到失败,距离下次签到时间还有" + (23 - hour) + "小时" + (59 - minute) + "分" + (59 - second) + "秒");
            }
            result.setChecked(false);
        }
        return result;
        }

    private static String GetLastTime(Long UserQQ){
        String Time;
        if (CheckInPlugin.getInstance().getPointData().GetTime(UserQQ)==null){
            Time="2022-10-21 00:00:00";
        }else{
            Time = CheckInPlugin.getInstance().getPointData().GetTime(UserQQ);
        }

        return Time;
    }

    public static String GetLocalTime(){
        Calendar calendar=new GregorianCalendar();
        Date date = new Date(calendar.getTimeInMillis());
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    public static Boolean CalculateMethod2(long UserQQ){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int NowYear=calendar.get(Calendar.YEAR);
        int NowMonth=calendar.get(Calendar.MONTH) + 1;
        int NowDate=calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(simpleDateFormat.parse(GetLastTime(UserQQ)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);


        return NowDate != date || NowMonth != month || NowYear != year;
    }


    }

package vip.fubuki.util;

import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalculateCode {
    public static boolean TimeCalculate(XiaoMingUser user) throws ParseException {
        Long UserQQ=user.getCode();
        Calendar calendar = new GregorianCalendar();
        long NowTime = calendar.getTimeInMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(simpleDateFormat.parse(GetLastTime(UserQQ)));
        long TargetTime = calendar.getTimeInMillis();
        long time = NowTime-TargetTime;
        long day = time/(1000*3600*24),hour = time/(1000*60*60),minute = time/(1000*60),second = time/(1000);
        second = second-minute*60;
        minute = minute-hour*60;
        hour = hour-day*24;
        if(day>=1){
            return true;
        }
        else {
            if (hour == 23) {
                if(minute==59){
                    user.sendMessage("签到失败,距离下次签到时间还有" + (59 - second) + "秒");
                }else {
                    user.sendMessage("签到失败,距离下次签到时间还有" + (59 - minute) + "分" + (59 - second) + "秒");
                }
            }
            else {
                user.sendMessage("签到失败,距离下次签到时间还有" + (23 - hour) + "小时" + (59 - minute) + "分" + (59 - second) + "秒");
            }
            return false;
        }
        }

    private static String GetLastTime(Long UserQQ){
        String Time;
        if (CheckInPlugin.pointData.GetTime(UserQQ)==null){
            Time="2022-10-21 00:00:00";
        }else{
            Time = CheckInPlugin.pointData.GetTime(UserQQ);
        }

        return Time;
    }

    public static String GetLocalTime(){
        Calendar calendar=new GregorianCalendar();
        Date date = new Date(calendar.getTimeInMillis());
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}

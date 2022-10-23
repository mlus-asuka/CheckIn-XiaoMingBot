package timeCalculate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class timeBetween {

    @Test
    public void Test() throws ParseException {
        Calendar calendar = new GregorianCalendar();
        long NowTime = calendar.getTimeInMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(simpleDateFormat.parse("2022-07-01 18:00:00"));
        long TargetTime = calendar.getTimeInMillis();
        long time = NowTime-TargetTime;
        long day = time/(1000*3600*24),hour = time/(1000*60*60),minute = time/(1000*60),second = time/(1000);
        second = second-minute*60;
        minute = minute-hour*60;
        hour = hour-day*24;
        System.out.println("距离下次签到时间还有"+hour+"小时"+minute+"分"+second+"秒");
    }

    @Test
    public void OutTime(){
        Calendar calendar=new GregorianCalendar();
        Date date = new Date(calendar.getTimeInMillis());
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));
    }
}

package timeCalculate;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class timeCompare{
    @Test
    public void CalculateMethod2(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        int NowYear=calendar.get(Calendar.YEAR);
        int NowMonth=calendar.get(Calendar.MONTH) + 1;
        int NowDate=calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(NowYear);
        System.out.println(NowMonth);
        System.out.println(NowDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(simpleDateFormat.parse("2022-10-23 11:59:59"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year);
        System.out.println(month);
        System.out.println(date);

        if(NowDate!=date||NowMonth!=month||NowYear!=year){
            System.out.println("True");
        }
        else{
            System.out.println("False");
        }
    }
}

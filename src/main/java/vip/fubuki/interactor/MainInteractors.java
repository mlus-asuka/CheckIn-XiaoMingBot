package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.Name;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.CalculateCode;
import vip.fubuki.util.Words;

import java.text.ParseException;


@SuppressWarnings("ALL")
public class MainInteractors extends SimpleInteractors<CheckInPlugin> {
    private Integer MaxPointPerTime;

    public void LoadConfiguration(){
        MaxPointPerTime = CheckInPlugin.configuration.getMaxPointPerTime();
    }

    @Name("CheckIn")
    @Filter(Words.CheckIn)
//    @RequireGroupTag("CheckIn")
    public void CheckIn(XiaoMingUser user){
        boolean Checked;

        try {
            Checked= CalculateCode.TimeCalculate(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(Checked){
          CheckInSucessful(user);
      }
    }

    public void CheckInSucessful(XiaoMingUser user){
        LoadConfiguration();
        Long userQQ = user.getCode();
        Integer Point;
        if(CheckInPlugin.pointData.getPoints(userQQ)==null){
            Point=0;
        }
        else{
            Point=CheckInPlugin.pointData.getPoints(userQQ);
        }

        Integer randomPlus=(int)(Math.random()*(MaxPointPerTime+1));
        Point=Point+randomPlus;
        CheckInPlugin.pointData.setPoints(userQQ,Point);
        CheckInPlugin.pointData.RefreshTime(userQQ,CalculateCode.GetLocalTime());
        user.sendMessage("签到成功，获得:"+randomPlus+"积分。");
    }

}

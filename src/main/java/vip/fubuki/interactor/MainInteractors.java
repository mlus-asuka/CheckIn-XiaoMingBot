package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.Name;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.GroupXiaoMingUser;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.CalculateCode;
import vip.fubuki.util.Words;



@SuppressWarnings("ALL")
public class MainInteractors extends SimpleInteractors<CheckInPlugin> {
    private Integer MaxPointPerTime;

    public void LoadConfiguration() {
        MaxPointPerTime = CheckInPlugin.getInstance().getConfiguration().getMaxPointPerTime();
    }

    @Name("CheckIn")
    @Filter(Words.CheckIn)
    public void CheckIn(XiaoMingUser user, GroupXiaoMingUser groupXiaoMingUser) {
        Long groupCode=groupXiaoMingUser.getGroupCode();
        Boolean enabled=CheckInPlugin.getInstance().getConfiguration().CheckEnabled(groupCode);
        if (enabled==null) enabled=false;
        if(enabled==true) {
            boolean Checked = false;

            if (CheckInPlugin.getInstance().getConfiguration().getWhetherRefreshInDawn()) {
                Checked = CalculateCode.CalculateMethod2(user);
            } else {
                Checked = CalculateCode.TimeCalculate(user);
            }


            if (Checked) {
                CheckInSucessful(user);
            }
        }
}

    public void CheckInSucessful(XiaoMingUser user){
        LoadConfiguration();
        Long userQQ = user.getCode();
        Integer Point;
        if(CheckInPlugin.getInstance().getPointData().getPoints(userQQ)==null){
            Point=0;
        }
        else{
            Point=CheckInPlugin.getInstance().getPointData().getPoints(userQQ);
        }

        Integer randomPlus=(int)(Math.random()*(MaxPointPerTime+1));
        Point=Point+randomPlus;
        CheckInPlugin.getInstance().getPointData().setPoints(userQQ,Point);
        CheckInPlugin.getInstance().getPointData().RefreshTime(userQQ,CalculateCode.GetLocalTime());
        user.sendMessage("签到成功，获得:"+randomPlus+"积分。");
    }
}

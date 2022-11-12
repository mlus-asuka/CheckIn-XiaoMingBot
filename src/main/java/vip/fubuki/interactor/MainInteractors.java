package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.Name;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.GroupXiaoMingUser;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.CalculateCode;
import vip.fubuki.util.Result;
import vip.fubuki.util.Words;



@SuppressWarnings("ALL")
public class MainInteractors extends SimpleInteractors<CheckInPlugin> {

    @Name("CheckIn")
    @Filter(Words.CheckIn)
    public void CheckIn(XiaoMingUser user, GroupXiaoMingUser groupXiaoMingUser) {
        long groupCode=groupXiaoMingUser.getGroupCode();
        Boolean enabled=CheckInPlugin.getInstance().getConfiguration().CheckEnabled(groupCode);
        if (enabled==null) enabled=false;
        if(enabled) {
            boolean Checked;

            if (CheckInPlugin.getInstance().getConfiguration().getWhetherRefreshInDawn()) {
                Checked = CalculateCode.CalculateMethod2(user.getCode());
                if (Checked) CheckInSucessful(user);
                else user.sendMessage("签到失败,今天已经签到过了。");
            } else {
                Result result=CalculateCode.TimeCalculate(user.getCode());
                Checked = result.getChecked();
                if (Checked) CheckInSucessful(user);
                else user.sendMessage(result.getMessage());
            }

        }
}
    public void CheckInSucessful(XiaoMingUser user){
        Long userQQ = user.getCode();
        Integer Point;
        if(CheckInPlugin.getInstance().getPointData().getPoints(userQQ)==null){
            Point=0;
        }
        else{
            Point=CheckInPlugin.getInstance().getPointData().getPoints(userQQ);
        }

        Integer randomPlus=(int)(Math.random()*(CheckInPlugin.getInstance().MaxPointPerTime+1));
        Point=Point+randomPlus;
        CheckInPlugin.getInstance().getPointData().setPoints(userQQ,Point);
        CheckInPlugin.getInstance().getPointData().RefreshTime(userQQ,CalculateCode.GetLocalTime());
        user.sendMessage("签到成功，获得:"+randomPlus+"积分。");
    }
}

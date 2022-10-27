package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Words;
import java.util.Map;

public class EnableGroupInteractors extends SimpleInteractors {
    @Required("checkin.admin.enable")
    @Filter(Words.ENABLE+" {group}")
    public void enableGroup(XiaoMingUser user, @FilterParameter("group") long group){
        Map<Long,Boolean> groupList=CheckInPlugin.getInstance().getConfiguration().getEnabledGroup();
        groupList.put(group,true);
        CheckInPlugin.getInstance().getConfiguration().setEnabledGroup(groupList);
        user.sendMessage("成功启用QQ群:"+group+"打卡功能。");
    }
    @Required("checkin.admin.enable")
    @Filter(Words.DISABLE+" {group}")
    public void disableGroup(XiaoMingUser user, @FilterParameter("group") long group){
        Map<Long,Boolean> groupList=CheckInPlugin.getInstance().getConfiguration().getEnabledGroup();
        groupList.remove(group);
        CheckInPlugin.getInstance().getConfiguration().setEnabledGroup(groupList);
        user.sendMessage("成功禁用QQ群:"+group+"打卡功能。");
    }
}

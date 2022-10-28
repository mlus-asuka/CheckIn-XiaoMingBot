package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import vip.fubuki.CheckInPlugin;

public class ReloadInteractors extends SimpleInteractors {
    @Required("checkin.admin.reload")
    @Filter("(重载|reload) 商店")
    public void Reload(){
        CheckInPlugin.getInstance().reload();
    }
}

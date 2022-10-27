package vip.fubuki.data;

import cn.chuanwise.xiaoming.preservable.SimplePreservable;
import vip.fubuki.CheckInPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataWriter extends SimplePreservable<CheckInPlugin> {

    Integer MaxPointPerTime = 1000;

    Boolean WhetherRefreshInDawn = false;

    Boolean EnableQuery = true;

    Boolean EnableTransfer = true;

    Long ShopOwner=(long)123;

    Map<Long,Boolean> EnabledGroup=new HashMap<>();

    public Integer getMaxPointPerTime() {
        return MaxPointPerTime;
    }

    public Boolean getWhetherRefreshInDawn() {
        return WhetherRefreshInDawn;
    }


    public Boolean getEnableQuery() {
        return EnableQuery;
    }

    public Boolean getEnableTransfer() {
        return EnableTransfer;
    }

    public Long getShopOwner() {return ShopOwner;}

    public Map<Long,Boolean> getEnabledGroup() {
        return EnabledGroup;
    }

    public void setEnabledGroup(Map<Long,Boolean> enabledGroup) {
        EnabledGroup = enabledGroup;
    }

    public Boolean CheckEnabled(long groupcode){
        return EnabledGroup.get(groupcode);
    }
}

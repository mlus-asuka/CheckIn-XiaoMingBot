package vip.fubuki.data;

import cn.chuanwise.xiaoming.preservable.SimplePreservable;
import vip.fubuki.CheckInPlugin;

public class DataWriter extends SimplePreservable<CheckInPlugin> {

    Integer MaxPointPerTime = 1000;

    Boolean WhetherRefreshInDawn = false;

    Boolean EnableQuery = true;

    Boolean EnableTransfer = true;

    Long ShopOwner=(long)123;

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

    String information = "别看了，以上功能暂时还没写。";
}

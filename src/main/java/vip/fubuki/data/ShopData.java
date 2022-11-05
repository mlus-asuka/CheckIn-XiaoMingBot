package vip.fubuki.data;

import cn.chuanwise.xiaoming.preservable.SimplePreservable;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Goods;

import java.util.HashMap;
import java.util.Map;

public class ShopData extends SimplePreservable<CheckInPlugin> {

    Integer ID_Index=0;
    Map<Integer, Goods> goods=new HashMap<>();

    public Goods getGoods(Integer id) {
        return goods.get(id);
    }

    public void setGoods(Integer id,Goods good) {
        goods.put(id,good);
        readyToSave();
    }

    public Integer GetIndex(){
        return ID_Index;
    }

    public void SetIndex(Integer index){
        ID_Index=index;
        readyToSave();
    }

}

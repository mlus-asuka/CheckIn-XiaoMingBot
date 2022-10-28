package vip.fubuki.data;

import cn.chuanwise.xiaoming.preservable.SimplePreservable;

import java.util.HashMap;
import java.util.Map;

public class UserData extends SimplePreservable {
    Map<Long,Integer> Points=new HashMap<>();

    Map<Long, String> LastChecked=new HashMap<>();

    public Integer getPoints(Long UserQQ) {
        return Points.get(UserQQ);
    }

    public void setPoints(Long UserQQ,Integer NewPoint) {
        Points.put(UserQQ,NewPoint);
        readyToSave();
    }

    public void removePoints(Long UserQQ){
        Points.remove(UserQQ);
    }

    public String GetTime(Long UserQQ){
        return LastChecked.get(UserQQ);
    }

    public void RefreshTime(Long UserQQ,String time){
        LastChecked.put(UserQQ,time);
        readyToSave();
    }

    public void RemoveData(Long UserQQ){
        LastChecked.remove(UserQQ);
    }

}

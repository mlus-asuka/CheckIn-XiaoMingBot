package vip.fubuki.util;

import vip.fubuki.CheckInPlugin;

public class Initialization {
    public void PrimaryData(Long UserQQ){
        CheckInPlugin.pointData.setPoints(UserQQ,0);
    }
}

package vip.fubuki.util;

import vip.fubuki.CheckInPlugin;

public class Initialization {
    public void PrimaryData(Long UserQQ){
        CheckInPlugin.getInstance().getPointData().setPoints(UserQQ,0);
    }
}

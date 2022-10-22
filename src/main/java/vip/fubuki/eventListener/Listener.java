package vip.fubuki.eventListener;

import cn.chuanwise.xiaoming.annotation.EventListener;
import cn.chuanwise.xiaoming.event.SimpleListeners;
import vip.fubuki.CheckInPlugin;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

public class Listener extends SimpleListeners<CheckInPlugin> {

//    Auto Remove Data When user quit group
    @EventListener
    public void OnLeaveEvent(MemberLeaveEvent event){
        CheckInPlugin.pointData.removePoints(event.getMember().getId());
        CheckInPlugin.pointData.RemoveData(event.getMember().getId());
    }

}

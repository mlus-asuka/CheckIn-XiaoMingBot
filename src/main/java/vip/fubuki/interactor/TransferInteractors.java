package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;

public class TransferInteractors extends SimpleInteractors<CheckInPlugin> {
    @Filter("转账 {ID} {Amount}")
    public void Transfer(XiaoMingUser user, @FilterParameter("ID") long ID,@FilterParameter("Amount") int Amount){
        Integer UserPoint=CheckInPlugin.pointData.getPoints(user.getCode());
        if(CheckInPlugin.configuration.getEnableTransfer()){
            if(UserPoint==null||CheckInPlugin.pointData.getPoints(ID)==null){
                user.sendMessage("没有对应的用户数据");
            }
            else{
            if(Amount<=0){
                user.sendMessage("非法的转账数目。");
            }
            else{
                if(Amount>UserPoint){
                    user.sendMessage("转账失败,积分不足。");
                }
                else{
                    UserPoint=UserPoint-Amount;
                    Integer ReceivePoint=CheckInPlugin.pointData.getPoints(ID);
                    ReceivePoint=ReceivePoint+Amount;
                    CheckInPlugin.pointData.setPoints(user.getCode(),UserPoint);
                    CheckInPlugin.pointData.setPoints(ID,ReceivePoint);
                    user.sendMessage("转账成功,当前积分:"+UserPoint);
                }
            }
            }
        }
        else user.sendMessage("当前管理员设置不允许转账");
    }
}

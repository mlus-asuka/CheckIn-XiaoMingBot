package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.GroupXiaoMingUser;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Words;

public class TransferInteractors extends SimpleInteractors<CheckInPlugin> {
    @Filter(Words.Transfer + " {qq} {Amount}")
    public void Transfer(XiaoMingUser user, @FilterParameter("qq") long qq, @FilterParameter("Amount") int Amount, GroupXiaoMingUser groupXiaoMingUser) {
        Long groupCode = groupXiaoMingUser.getGroupCode();
        Boolean enabled = CheckInPlugin.getInstance().getConfiguration().CheckEnabled(groupCode);
        if (enabled == null) enabled = false;
        if (enabled == true) {
            Integer UserPoint = CheckInPlugin.getInstance().getPointData().getPoints(user.getCode());
            if (CheckInPlugin.getInstance().getConfiguration().getEnableTransfer()) {
                if (UserPoint == null || CheckInPlugin.getInstance().getPointData().getPoints(qq) == null) {
                    user.sendMessage("没有对应的用户数据");
                } else {
                    if (user.getCode() != qq) {
                        if (Amount <= 0) {
                            user.sendMessage("非法的转账数目。");
                        } else {
                            if (Amount > UserPoint) {
                                user.sendMessage("转账失败,积分不足。");
                            } else {
                                UserPoint = UserPoint - Amount;
                                CheckInPlugin.getInstance().getPointData().setPoints(user.getCode(), UserPoint);
                                CheckInPlugin.getInstance().getPointData().setPoints(qq, CheckInPlugin.getInstance().getPointData().getPoints(qq) + Amount);
                                user.sendMessage("转账成功,当前积分:" + UserPoint);
                            }
                        }
                    } else user.sendMessage("不能给自己转账。");
                }
            } else user.sendMessage("当前管理员设置不允许转账");
        }
    }
}

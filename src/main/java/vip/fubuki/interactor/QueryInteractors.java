package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Name;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Words;

@SuppressWarnings("ALL")
public class QueryInteractors extends SimpleInteractors<CheckInPlugin> {

   @Name("UserQuery")
   @Filter("我的积分")
    public void Query(XiaoMingUser user){
       if(CheckInPlugin.configuration.getEnableQuery()) {
           if (CheckInPlugin.pointData.getPoints(user.getCode()) == null) {
               user.sendMessage("你有个锤子积分。");
               CheckInPlugin.pointData.setPoints(user.getCode(), 0);
           } else {
               user.sendMessage("当前积分:" + CheckInPlugin.pointData.getPoints(user.getCode()));
           }
       }
       else{
           user.sendMessage("当前管理员配置不允许查看积分。");
       }
   }

   @Name("AdminQuery")
   @Required("admin")
   @Filter(Words.QUERY)
   @Filter(Words.QUERY+" {qq}")
    public void AdminQuery(XiaoMingUser user, @FilterParameter(value = "qq",defaultValue = "123") long qq) {
       if (qq == 123) {
           qq = user.getCode();
       }
       if (CheckInPlugin.pointData.getPoints(qq) == null) {
           user.sendMessage("当前用户尚未有积分记录。");
       } else {
           String name = getXiaoMingBot().getContactManager().getPrivateContactPossibly(qq).get(0).getName();
           user.sendMessage("用户:" +name+ ",积分为:" + CheckInPlugin.pointData.getPoints(qq));
       }
   }
}

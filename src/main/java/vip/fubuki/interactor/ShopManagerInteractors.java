package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Required;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Goods;

@SuppressWarnings("ALL")
public class ShopManagerInteractors extends SimpleInteractors<CheckInPlugin> {
    @Required("admin")
    @Filter("上架"+" {Name} {Price} {Amount}")
    public void Carriage(XiaoMingUser user, @FilterParameter("Name") String name,@FilterParameter("Price") int Price,@FilterParameter(value = "Amount",defaultValue = "-1") int Amount){
        Integer index=CheckInPlugin.shopData.GetIndex()+1;
        CheckInPlugin.shopData.SetIndex(index);
        Goods NewGood=new Goods();
        NewGood.setID(index);
        NewGood.setName(name);
        NewGood.setPrice(Price);
        if(Amount<0){Amount=-1;}
        NewGood.setAmount(Amount);
        CheckInPlugin.shopData.setGoods(index,NewGood);
        user.sendMessage("成功上架一样名称为:"+name+",价格为:"+Price+",存量为:"+Amount+"的货品，ID:"+index);
    }

    @Required("admin")
    @Filter("下架 {ID}")
    public void UnderCarriaged(XiaoMingUser user,@FilterParameter("ID") int id) {
        if (id <= 0 || id > CheckInPlugin.shopData.GetIndex()) {
            user.sendMessage("操作失败,没有此ID的商品。");
        }
        else if(!CheckInPlugin.shopData.getGoods(id).getBoolean()){
            user.sendMessage("这件商品原本就是下架的。");
        }
        else{
            CheckInPlugin.shopData.getGoods(id).setBoolean(true);
            user.sendMessage("成功下架ID" + id + "的商品。");
        }
    }

    @Required("admin")
    @Filter("补货 {ID} {Amount}")
    public void Replenishment(XiaoMingUser user,@FilterParameter("ID") int id,@FilterParameter("Amount") int Amount){
        if (id <= 0 || id > CheckInPlugin.shopData.GetIndex()) {
            user.sendMessage("操作失败,没有此ID的商品。");
        }
        else{
            Goods goods=CheckInPlugin.shopData.getGoods(id);
            int PreAmount=goods.getAmount();
            PreAmount=PreAmount+Amount;
            if(PreAmount<0){PreAmount=-1;}
            goods.setAmount(PreAmount);
            CheckInPlugin.shopData.setGoods(id,goods);
            user.sendMessage("补货成功,当前余量:"+PreAmount+"\n提示:当余量为-1时将被认为是不限量的。");
        }
    }

    @Required("admin")
    @Filter("改价格 {ID} {Price}")
    public void ChangePrice(XiaoMingUser user,@FilterParameter("ID") int ID,@FilterParameter("Price") int Price){
        Goods good =CheckInPlugin.shopData.getGoods(ID);
        good.setPrice(Price);
        CheckInPlugin.shopData.setGoods(ID,good);
        user.sendMessage("成功将商品ID:"+ID+"的物品价格更改为:"+Price);
    }
    @Required("admin")
    @Filter("设置积分 {User} {Point}")
    public void SetPoint(XiaoMingUser user,@FilterParameter("User") long qq,@FilterParameter("Point") int Point) {
        if (CheckInPlugin.pointData.getPoints(qq) != null) {
            CheckInPlugin.pointData.setPoints(qq, Point);
            user.sendMessage("成功设置用户:" + getXiaoMingBot().getContactManager().getPrivateContactPossibly(qq).get(0).getName() + "的积分为:" + Point);
        }
        else user.sendMessage("该用户还没有记录。");
    }
    @Required("admin")
    @Filter("加积分 {User} {Point}")
    public void AddPoint(XiaoMingUser user,@FilterParameter("User") long qq,@FilterParameter("Point") int Point) {
        Integer Former = CheckInPlugin.pointData.getPoints(qq);
        CheckInPlugin.pointData.setPoints(qq, Former + Point);
        if (Former != null) {
            user.sendMessage("成功为用户" + getXiaoMingBot().getContactManager().getPrivateContactPossibly(qq).get(0).getName() + "加了" + Point + "积分");
        }
        else user.sendMessage("该用户还没有记录。");
    }
    }

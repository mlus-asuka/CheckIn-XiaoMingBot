package vip.fubuki.interactor;

import cn.chuanwise.xiaoming.annotation.Filter;
import cn.chuanwise.xiaoming.annotation.FilterParameter;
import cn.chuanwise.xiaoming.annotation.Name;
import cn.chuanwise.xiaoming.interactor.SimpleInteractors;
import cn.chuanwise.xiaoming.user.GroupXiaoMingUser;
import cn.chuanwise.xiaoming.user.XiaoMingUser;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Goods;
import vip.fubuki.util.Initialization;
import vip.fubuki.util.Words;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class ShopInteractors extends SimpleInteractors<CheckInPlugin> {
    private Integer LastPage = 1;
    final List<String> Goodlist = new ArrayList<>();

    @Name("Shop")
    @Filter(Words.Shop)
    @Filter(Words.Shop + " {page}")
    public void Shop(XiaoMingUser user, @FilterParameter(value = "page", defaultValue = "1") int page, GroupXiaoMingUser groupXiaoMingUser) {
        Long groupCode = groupXiaoMingUser.getGroupCode();
        Boolean enabled = CheckInPlugin.getInstance().getConfiguration().CheckEnabled(groupCode);
        if (enabled==null) enabled=false;
        if (enabled == true) {
            Integer index = CheckInPlugin.getInstance().getShopData().GetIndex();
            Integer Scanned = 0;
            LastPage = page;
            if (index != 0) {
                for (int i = 1; i <= index; i++) {
                    Goods goods = CheckInPlugin.getInstance().getShopData().getGoods(i);
                    String name = goods.getName();
                    Integer price = goods.getPrice();
                    Integer amount = goods.getAmount();
                    Boolean WhetherUnderCarriaged = goods.getBoolean();
                    if (!WhetherUnderCarriaged) {
                        if (amount == -1) {
                            Goodlist.add(Scanned, "商品ID:" + i + ",商品名称:" + name + ",价格:" + price + "积分," + "余量:无限" + "\n");
                        } else
                            Goodlist.add(Scanned, "商品ID:" + i + ",商品名称:" + name + ",价格:" + price + "积分," + "余量:" + amount + "\n");
                        Scanned++;
                    }
                }
                float a=(float) Scanned;
                float b=(float) 10;
                int MaxPage= (int) Math.ceil(a/b);
                String text = "商店页面 当前页:" + page + "/" +MaxPage + "\n";
                if ((page - 1) * 10 > Scanned || page == 0) {
                    user.sendMessage("页码超出商品列表。");
                } else {
                    for (int i = (page * 10 - 9); i <= page * 10; i++) {
                        text = text + Goodlist.get(i - 1);
                        if (i == Scanned) {
                            break;
                        }
                    }
                    if(page==1 && MaxPage==1)  text = text + "当前为唯一页 回复退出以结束查询";
                    else if (page==1) text = text + "回复  下一页 切换页面 回复退出以结束查询";
                    else text = text + "回复 上一页 / 下一页 切换页面 回复退出以结束查询";
                    user.addTag("QueringShop");
                    user.sendMessage(text);

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            user.removeTag("QueringShop");
                        }
                    };
                    getXiaoMingBot().getScheduler().runLater(120000, runnable);
                }
            } else {
                user.sendMessage("抱歉，商店内暂时没有出售物品，请联系管理员上架。");
            }
        }
    }

    @Filter(Words.Previous)
    public void PrePage(XiaoMingUser user, GroupXiaoMingUser groupXiaoMingUser) {
        if (user.hasTag("QueringShop")) {
            Shop(user, LastPage - 1, groupXiaoMingUser);
        }
    }

    @Filter(Words.Next)
    public void NextPage(XiaoMingUser user, GroupXiaoMingUser groupXiaoMingUser) {
        if (user.hasTag("QueringShop")) {
            Shop(user, LastPage + 1, groupXiaoMingUser);
        }
    }

    @Filter(Words.Quit)
    public void QuitQuery(XiaoMingUser user) {
        if (user.hasTag("QueringShop")) {
            user.removeTag("QueringShop");
        }
    }

    @Filter(Words.BUY + " {id}")
    public void Buy(XiaoMingUser user, @FilterParameter("id") int id, GroupXiaoMingUser groupXiaoMingUser) {
        Long groupCode = groupXiaoMingUser.getGroupCode();
        Boolean enabled = CheckInPlugin.getInstance().getConfiguration().CheckEnabled(groupCode);
        if (enabled==null) enabled=false;
        if (enabled == true) {
            Goods Buying = CheckInPlugin.getInstance().getShopData().getGoods(id);
            Integer UserPoint = CheckInPlugin.getInstance().getPointData().getPoints(user.getCode());
            Long ShopOwner = CheckInPlugin.getInstance().getConfiguration().getShopOwner();
            if (Buying == null) {
                user.sendMessage("购买失败，没有此ID对应的商品");
            }
            else if (Buying.getAmount() == 0) {
                user.sendMessage("抱歉，此商品暂无存货。");
            }else if(Buying.getBoolean()) {
                user.sendMessage("购买失败,该商品已下架。");
            }
            else {
                Integer Price = Buying.getPrice();
                Integer Amount = Buying.getAmount();
                if (UserPoint < Price) {
                    user.sendMessage("积分不足,购买失败,当前积分:" + UserPoint + ",所需积分:" + Price);
                } else if (UserPoint == null) {
                    Initialization primary = new Initialization();
                    primary.PrimaryData(user.getCode());
                    user.sendMessage("你买个锤子呢,你有分吗？");
                } else {
                    if (ShopOwner == 123) {
                        user.sendMessage("购买失败,商店管理员未配置,请联系管理员。");
                    } else {
                        UserPoint = UserPoint - Price;
                        CheckInPlugin.getInstance().getPointData().setPoints(user.getCode(), UserPoint);
                        if (Amount != -1) {
                            Amount = Amount - 1;
                            Buying.setAmount(Amount);
                            CheckInPlugin.getInstance().getShopData().setGoods(id, Buying);
                        }
                        String GoodName = Buying.getName();
                        user.sendMessage("成功购买一件" + GoodName + ",当前积分:" + UserPoint + ",物品进入待发送队列，请耐心等待。");
                        getXiaoMingBot().getContactManager().getPrivateContact(ShopOwner).get().sendMessage("用户:" + user.getName() + ",ID:" + user.getCode() + ",兑换了一件" + GoodName + ",正在等待你发货");
                    }
                }
            }
        }
    }
}

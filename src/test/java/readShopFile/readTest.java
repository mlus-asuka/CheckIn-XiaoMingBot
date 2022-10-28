package readShopFile;

import org.junit.jupiter.api.Test;
import vip.fubuki.CheckInPlugin;
import vip.fubuki.util.Goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class readTest {
    Integer index=2;
    Integer Scanned=0;
    List<String> Goodlist=new ArrayList<>();

    Map<Integer,Goods> goods_list=new HashMap<>();

    Goods good1=new Goods();
    Goods good2=new Goods();

    public void Build(){
        good1.setID(1);
        good1.setName("光辉石");
        good1.setPrice(10000);
        good1.setAmount(100);
        good1.setBoolean(false);
        good2.setID(2);
        good2.setName("邀请码");
        good2.setPrice(2000);
        good2.setAmount(-1);
        good2.setBoolean(false);
        goods_list.put(1,good1);
        goods_list.put(2,good2);
    }

    @Test
    public void readTest(){
        Build();
        for (int i = 1; i <= index;i++) {
            Goods goods = goods_list.get(i);
            String name = goods.getName();
            Integer price = goods.getPrice();
            Integer amount = goods.getAmount();
            Boolean WhetherUnderCarriaged=goods.getBoolean();
            if(!WhetherUnderCarriaged){
                Goodlist.add(Scanned,"商品ID:"+i+",商品名称:"+name+",价格:"+price+"积分,"+"余量:"+amount+"\n");
                Scanned++;
            }
        }
        System.out.println(Goodlist.get(0));
        System.out.println(Goodlist.get(1));
    }
}

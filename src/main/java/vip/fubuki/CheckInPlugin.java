package vip.fubuki;

import cn.chuanwise.xiaoming.plugin.JavaPlugin;
import vip.fubuki.data.DataWriter;
import vip.fubuki.data.ShopData;
import vip.fubuki.data.UserData;
import vip.fubuki.eventListener.Listener;
import vip.fubuki.interactor.MainInteractors;
import vip.fubuki.interactor.QueryInteractors;
import vip.fubuki.interactor.ShopInteractors;
import vip.fubuki.interactor.ShopManagerInteractors;

import java.io.File;

public class CheckInPlugin extends JavaPlugin {
    private static final CheckInPlugin INSTANCE = new CheckInPlugin();

    public static CheckInPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        getXiaoMingBot().getInteractorManager().registerInteractors(new MainInteractors(),this);
        getXiaoMingBot().getInteractorManager().registerInteractors(new ShopInteractors(),this);
        getXiaoMingBot().getInteractorManager().registerInteractors(new QueryInteractors(),this);
        getXiaoMingBot().getInteractorManager().registerInteractors(new ShopManagerInteractors(),this);
        getXiaoMingBot().getEventManager().registerListeners(new Listener(), CheckInPlugin.getInstance());
        getLogger().info("打卡插件启动成功。");
    }

    public static DataWriter configuration;
    public static UserData pointData;

    public static ShopData shopData;

    @Override
    @SuppressWarnings("all")
    public void onLoad() {
        reload();
    }

    public void reload(){
        final File dataFolder = getDataFolder();
        dataFolder.mkdirs();
        configuration = setupConfiguration(DataWriter.class,"configuration.json",DataWriter::new);
        pointData = setupConfiguration(UserData.class,"point.json",UserData::new);
        shopData = setupConfiguration(ShopData.class,"shop.json",ShopData::new);
    }

}


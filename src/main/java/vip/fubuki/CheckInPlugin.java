package vip.fubuki;

import cn.chuanwise.xiaoming.plugin.JavaPlugin;
import vip.fubuki.data.DataWriter;
import vip.fubuki.data.ShopData;
import vip.fubuki.data.UserData;
import vip.fubuki.eventListener.Listener;
import vip.fubuki.interactor.*;

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
        getXiaoMingBot().getInteractorManager().registerInteractors(new TransferInteractors(),this);
        getXiaoMingBot().getInteractorManager().registerInteractors(new EnableGroupInteractors(),this);
        getXiaoMingBot().getEventManager().registerListeners(new Listener(), CheckInPlugin.getInstance());
        getLogger().info("打卡插件启动成功。");
    }

    protected DataWriter configuration;
    protected UserData pointData;

    protected ShopData shopData;

    public DataWriter getConfiguration() {
        return configuration;
    }

    public UserData getPointData() {
        return pointData;
    }

    public ShopData getShopData() {
        return shopData;
    }

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


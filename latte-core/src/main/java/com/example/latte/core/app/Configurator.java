package com.example.latte.core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Administrator on 2017/11/13.
 */

/**
 * 该类是用来进行一些配置文件的存储和获取的
 */
public class Configurator {
    //不用WeakHashMap的原因: WeakHashMap的机制是当里面的键值对不再被引用的时候自行进行清除和回收
    //而Configurator里的配置项是伴随Activity生命周期存在而存在的，所以不合适
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }


    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * ConfigKeys.CONFIG_READY置true 表示初始化配置完成
     */
    public final void configure() {
        initIcons();
        //TODO 10-8 02：30 之前这里有Logger.init()方法？ 还有下面的方法有啥用？
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * 初始化Icons
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            //因为gitHub上初始化是用的建造者模式
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 先加到ICONS中，然后在 initIcons中初始化
     * 添加方法看 FontEcModule 类
     * @param descriptor
     * @return
     */
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    /**
     * javaScript注入的名字，即在js中调用原生的方法
     * @param name
     * @return
     */
    public Configurator withJavascriptInterface(@NonNull String name) {
        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    /**
     * 添加js中要调用的方法，即跟action的值对应的每个方法
     * event是原生和web交互的介质类，暂时不用
     */
//    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
//        final EventManage manager = EventManage.getInstance();
//        manager.addEvent(name, event);
//        return this;
//    }

    //浏览器加载的HOST
    public  Configurator withWebHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.WEB_HOST,host);
        return this;
    }

    /**
     * 检测是否配置好
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    //TODO 以后解决  了解泛型的使用
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }
}

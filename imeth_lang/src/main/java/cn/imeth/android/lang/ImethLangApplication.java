package cn.imeth.android.lang;

import android.app.Application;

import cn.imeth.android.lang.utils.Androids;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/27.
 */
public class ImethLangApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Androids.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Androids.clear();
    }
}

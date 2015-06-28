package cn.imeth.app;

import cn.imeth.android.ImethApplication;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.lang.log.Log;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/25.
 */
public class AppApplication extends ImethApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderUtils.init(this);


        Log.i("imeth", "XXXX", new Exception("yyyy"));

    }

}

package cn.imeth.app;

import java.io.File;

import cn.imeth.android.ImethApplication;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.log.Log;
import cn.imeth.android.log.LogConfig;
import cn.imeth.android.thread.ThreadPool;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/25.
 */
public class AppApplication extends ImethApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();

        ImageLoaderUtils.init(this);
    }

    private void initLogger() {

        if (!ThreadPool.isInitialize()) {
            ThreadPool.initThreadPool(3);
        }

        LogConfig config = new LogConfig();
        config.setLogFile(true);
        config.setLogFilePath(getExternalFilesDir(null).getAbsolutePath() + File.separator + "imeth.log");
        Log.initLogger(config);

        Log.i("imeth","Log init!");
    }
}

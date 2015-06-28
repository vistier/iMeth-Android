package cn.imeth.android;

import cn.imeth.android.lang.ImethLangApplication;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class ImethApplication extends ImethLangApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initDefaultUncaughtExceptionHandler();
    }

    private void initDefaultUncaughtExceptionHandler() {
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                if (!ImethApplication.this.uncaughtException(thread, ex) && defaultUncaughtExceptionHandler != null) {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
                }

            }
        });

    }

    /**
     * 处理没有捕捉的异常
     *
     * @param thread
     * @param ex
     * @return 如果处理完毕返回true
     */
    public boolean uncaughtException(Thread thread, Throwable ex) {

        return false;
    }


}

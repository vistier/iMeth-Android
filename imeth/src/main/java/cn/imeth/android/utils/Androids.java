package cn.imeth.android.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import cn.imeth.android.lang.Toasts;

/**
 * Created by Administrator on 2015/5/8.
 */
public class Androids {

    static Application application;
    static Toasts toasts;

    @Deprecated
    public static void init(Application application) {

        Androids.application = application;

        initDisplay(application);
        initTelephony(application);
        initStatus(application);

        initToast();
    }

    private static void initDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        Display.width = wm.getDefaultDisplay().getWidth();
        Display.height = wm.getDefaultDisplay().getHeight();
        Display.density = metrics.density;
        Display.densityDpi = metrics.densityDpi;
        Display.scaledDensity = metrics.scaledDensity;
    }

    private static void initTelephony(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        DEVICE_ID = manager.getDeviceId();
    }

    public static void initStatus(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            IS_NETWORK_CONNECTED = mNetworkInfo.isAvailable();
        }
    }

    public static void initToast() {
        toasts = new Toasts();
    }

    public static class Display {
        /** 设备的屏幕宽度 */
        public static int width;
        /** 设备的屏幕高度 */
        public static int height;
        public static float density;
        public static int densityDpi;
        public static float scaledDensity;
    }

    /**
     * <ul>
     * <li>API 1 Android 1.0 </li>
     * <li>API 2 Android 1.1</li>
     * <li>API 3 Android 1.5</li>
     * <li>API 4 Android 1.6</li>
     * <li>API 5 Android 2.0</li>
     * <li>API 6 Android 2.0.1</li>
     * <li>API 7 Android 2.1</li>
     * <li>API 8 Android 2.2</li>
     * <li>API 9 Android 2.3</li>
     * <li>API 10 Android 2.3.3</li>
     * <li>API 11 Android 3.0</li>
     * <li>API 12 Android 3.1</li>
     * <li>API 13 Android 3.2</li>
     * <li>API 14 Android 4.0</li>
     * <li>API 15 Android 4.0.3</li>
     * <li>API 16 Android 4.1</li>
     * <li>API 17 Android 4.2</li>
     * <li>API 18 Android 4.3</li>
     * <li>API 19 Android 4.4</li>
     * <li>API 20 Android 4.4.2</li>
     * <li>API 21 Android 5.0</li>
     * </ul>
     */
    public static int VERSION_CODE = Build.VERSION.SDK_INT;

    public static String VERSION_RELEASE = Build.VERSION.RELEASE;

    public static String BRAND = Build.BRAND;

    public static String MODEL = Build.MODEL;

    public static String DEVICE_ID = "imeth";

    public static String SERIAL = Build.SERIAL;

    public static boolean IS_NETWORK_CONNECTED = true;

    public static Toast makeText(CharSequence message) {

        if (toasts != null) {
            initToast();
        }

        return toasts.makeText(application, message);
    }

    public static String getString(int res) {
        return application.getResources().getString(res);
    }

    public static int getColor(int res) {
        return application.getResources().getColor(res);
    }

    //// ****************

    /**
     * 获得字体的缩放密度
     *
     * @return
     */
    public static float getScaledDensity() {
        return Display.scaledDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * Display.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / Display.density + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        return (int) (pxValue / Display.scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        return (int) (spValue * Display.scaledDensity + 0.5f);
    }

    //// *****************

    /**
     * 获取状态栏高度＋标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    public static void clear() {
        toasts.close();
        toasts = null;
    }

}

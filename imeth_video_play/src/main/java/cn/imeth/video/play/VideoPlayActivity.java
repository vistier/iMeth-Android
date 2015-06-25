package cn.imeth.video.play;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.cyberplayer.sdk.BCyberPlayerFactory;
import com.baidu.cyberplayer.sdk.BEngineManager;

import cn.imeth.video.R;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/20.
 */
public class VideoPlayActivity extends Activity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VideoPlayActivity.class);

        context.startActivity(intent);
    }

    //您的ak
    private static String AK = "v6muxu0mCjKbThkZ42e37wx7";
    //您的sk的前16位
    private static String SK = "ylLah21UzbDg3XTF5Czk8unX0Mm2Ljh0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置根据重力感应切换横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        // 去掉信息栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.video_play_activity);

        // 初始化BCyberPlayerFactory, 在其他任何接口调用前需要先对BCyberPlayerFactory进行初始化
        BCyberPlayerFactory.init(this);

        // 检查引擎是否存在
        checkEngineInstalled();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BCyberPlayerFactory.resetProxyFactory();
    }

    protected void onInitVideoPlayFragment() {
        BEngineManager mgr = BCyberPlayerFactory.createEngineManager();
        mgr.initCyberPlayerEngine(AK, SK);

        getFragmentManager().beginTransaction().add(R.id.container, new VideoPlayFragment()).commit();
    }

    /**
     * 检测engine是否安装,如果没有安装需要安装engine
     */
    private void checkEngineInstalled() {
        if (isEngineInstalled()) {
            // 已经安装
            onInitVideoPlayFragment();
        } else {
            getFragmentManager().beginTransaction().add(R.id.container, new InstallEngineFragment()).commit();
            //安装engine

        }
    }

    protected void onInstalledEngine() {
        onInitVideoPlayFragment();
    }

    /**
     * 检测engine是否安装
     *
     * @return
     */
    private boolean isEngineInstalled() {
        BEngineManager mgr = BCyberPlayerFactory.createEngineManager();
        return mgr.EngineInstalled();
    }


}

package cn.imeth.video.play;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.cyberplayer.sdk.BCyberPlayerFactory;
import com.baidu.cyberplayer.sdk.BEngineManager;

import cn.imeth.video.R;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/20.
 */
public class InstallEngineFragment extends Fragment {

    ProgressBar progressBar;
    TextView promptValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BEngineManager mgr = BCyberPlayerFactory.createEngineManager();
        mgr.installAsync(new BEngineManager.OnEngineListener() {

            @Override
            public boolean onPrepare() {
                return true;
            }

            @Override
            public int onDownload(int total, int current) {
                if (progressBar != null) {
                    progressBar.setMax(total);
                    progressBar.setProgress(current);
                }

                return DOWNLOAD_CONTINUE;
            }

            @Override
            public int onPreInstall() {
                return DOWNLOAD_CONTINUE;
            }

            @Override
            public void onInstalled(int result) {
                if (result == BEngineManager.OnEngineListener.RET_NEW_PACKAGE_INSTALLED) {
                    //安装完成
                    if (getActivity()!=null && getActivity() instanceof VideoPlayActivity) {
                        ((VideoPlayActivity) getActivity()).onInstalledEngine();
                    }
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.install_engine_fragment, null);

        progressBar = findViewById(view, R.id.progress_bar);
        promptValue = findViewById(view, R.id.prompt_value);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //下载并安装engine

        BEngineManager mgr = BCyberPlayerFactory.createEngineManager();
        mgr.installAsync(new BEngineManager.OnEngineListener() {

            @Override
            public boolean onPrepare() {
                return true;
            }

            @Override
            public int onDownload(int total, int current) {
                progressBar.setMax(total);
                progressBar.setProgress(current);

                return DOWNLOAD_CONTINUE;
            }

            @Override
            public int onPreInstall() {
                return DOWNLOAD_CONTINUE;
            }

            @Override
            public void onInstalled(int result) {
                if (result == BEngineManager.OnEngineListener.RET_NEW_PACKAGE_INSTALLED) {
                    //安装完成
                    if (getActivity() instanceof VideoPlayActivity) {
                        ((VideoPlayActivity) getActivity()).onInstalledEngine();
                    }
                }
            }
        });

    }

    public <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }
}

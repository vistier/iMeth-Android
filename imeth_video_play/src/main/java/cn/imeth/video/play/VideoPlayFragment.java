package cn.imeth.video.play;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.cyberplayer.sdk.BVideoView;

import cn.imeth.video.ImethMediaController;
import cn.imeth.video.ImethVideoView;
import cn.imeth.video.R;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/20.
 */
public class VideoPlayFragment extends Fragment implements
        BVideoView.OnPreparedListener,
        BVideoView.OnCompletionListener,
        BVideoView.OnErrorListener,
        BVideoView.OnInfoListener,
        BVideoView.OnPlayingBufferCacheListener {

    private View view;

    private ImethVideoView videoView;
    private ImethMediaController mediaController;

    private int position = 0;
    private PlayerStatus status = PlayerStatus.PLAYER_IDLE;

    private String url = "http://bcs.duapp.com/dlna-sample/out_MP4_AVC_AAC_320x240_2013761628.mp4?sign=MBO:C09e40adc8851224375a26cf2c6d12a0:7zwy3HtoM%2B5hXB2%2FlJFN6OkWFCs%3D";

    private Object SyncPlaying = new Object();
    EventHandler handler;
    HandlerThread handlerThread;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlerThread = new HandlerThread("video play event handler thread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        handler = new EventHandler(handlerThread.getLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.video_playing_layout, null);

            videoView = findViewById(view, R.id.video_view);
            mediaController = findViewById(view, R.id.media_controller);

        } else {
            container.removeView(view);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);
        videoView.setOnInfoListener(this);
        videoView.setOnPlayingBufferCacheListener(this);

        videoView.setMediaController(mediaController);

    }

    @Override
    public void onResume() {
        super.onResume();

        videoView.resume();
        //videoView.start();
        handler.sendEmptyMessage(EventHandler.EVENT_PLAY);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (status == PlayerStatus.PLAYER_PREPARING) {
            position = videoView.getCurrentPosition();
        }

        videoView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {

            if (videoView != null) {
                videoView.stopPlayback();
            }

            handlerThread.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onPrepared() {
        Log.d("imeth VideoPlayFragment", "onPrepared");
        status = PlayerStatus.PLAYER_PREPARED;
    }

    @Override
    public void onCompletion() {
        Log.d("imeth VideoPlayFragment", "onCompletion");
        // 播放完成
        synchronized (SyncPlaying) {
            SyncPlaying.notify();
        }

        status = PlayerStatus.PLAYER_IDLE;

        getActivity().finish();
    }

    @Override
    public boolean onError(int what, int extra) {
        Log.d("imeth VideoPlayFragment", "onError what: " + what + " , extra: " + extra);
        synchronized (SyncPlaying) {
            SyncPlaying.notify();
        }

        status = PlayerStatus.PLAYER_IDLE;

        getActivity().finish();

        return true;
    }

    @Override
    public boolean onInfo(int what, int extra) {
        Log.d("imeth VideoPlayFragment", "onInfo what: " + what + " , extra: " + extra);
        return true;
    }

    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     *
     * @param percent
     */
    @Override
    public void onPlayingBufferCache(int percent) {
        Log.d("imeth VideoPlayFragment", "onPlayingBufferCache percent: " + percent);
        // 缓冲缓存
    }


    /**
     * 播放状态
     */
    private enum PlayerStatus {
        /**
         * 闲置
         */
        PLAYER_IDLE,
        /**
         * 播放中
         */
        PLAYER_PREPARING,
        /**
         * 播放结束
         */
        PLAYER_PREPARED,
    }

    class EventHandler extends Handler {

        public static final int EVENT_PLAY = 1;

        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PLAY:

                    if (status != PlayerStatus.PLAYER_IDLE) {
                        synchronized (SyncPlaying) {
                            try {
                                SyncPlaying.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    videoView.setVideoPath(url);

                    if (position > 0) {
                        videoView.seekTo(position);
                        position = 0;
                    }

                    videoView.start();

                    status = PlayerStatus.PLAYER_PREPARING;

                    break;
            }
        }
    }

    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }


}

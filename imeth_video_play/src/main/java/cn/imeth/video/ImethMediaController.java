package cn.imeth.video;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/20.
 */
public class ImethMediaController extends LinearLayout implements ImethVideoView.OnPreparedListener {

    private final int UI_EVENT_UPDATE_CURR_POSITION = 1;

    ImethVideoView videoView;

    TextView timeValue = null;
    SeekBar seekBar;
    ImageButton playBtn;


    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_EVENT_UPDATE_CURR_POSITION://更新进度及时间
                    int currPosition = videoView.getCurrentPosition();
                    int duration = videoView.getDuration();

                    timeValue.setText(String.format("%s/%s",formatTime(currPosition), formatTime(duration)));

                    seekBar.setMax(duration);
                    seekBar.setProgress(currPosition);

                    handler.sendEmptyMessageDelayed(UI_EVENT_UPDATE_CURR_POSITION, 800);
                    break;
                default:
                    break;
            }
        }

    };

    public ImethMediaController(Context context) {
        super(context);
        initView();
    }

    public ImethMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ImethMediaController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.media_controller_layout, null);

        timeValue = findViewById(view, R.id.time_value);
        playBtn = findViewById(view, R.id.play_btn);
        seekBar = findViewById(view, R.id.seek_bar);

        addView(view);

        initListener();
    }

    private void initListener(){
        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    pause();
                } else {
                    play();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int duration = videoView.getDuration();
                timeValue.setText(String.format("%s/%s",formatTime(progress), formatTime(duration)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(UI_EVENT_UPDATE_CURR_POSITION);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 修改进度
                videoView.seekTo(seekBar.getProgress());
                handler.sendEmptyMessage(UI_EVENT_UPDATE_CURR_POSITION);
            }
        });
    }

    public void onStop() {
        handler.removeMessages(UI_EVENT_UPDATE_CURR_POSITION);
    }

    public void play(){
        videoView.resume();
    }

    public void pause() {
        videoView.pause();
    }

    @Override
    public void onPrepared() {
        // 开始播放
        handler.sendEmptyMessage(UI_EVENT_UPDATE_CURR_POSITION);
    }

    private static String formatTime(int second) {
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp = null;
        if (0 != hh) {
            return String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            return String.format("%02d:%02d", mm, ss);
        }
    }

    public <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }
}

package cn.imeth.video;

import android.content.Context;
import android.util.AttributeSet;

import com.baidu.cyberplayer.sdk.BVideoView;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/20.
 */
public class ImethVideoView extends BVideoView {

    ImethMediaController controller;

    public ImethVideoView(Context context) {
        super(context);
    }

    public ImethVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImethVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onStart() {

    }

    protected void onResume() {

    }

    protected void onPause() {
        controller.onStop();
    }

    protected void onStop() {

    }

    private OnPreparedListener onPreparedListener;

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {
        super.setOnPreparedListener(listener);
        this.onPreparedListener = listener;
    }

    public OnPreparedListener getOnPreparedListener() {
        return onPreparedListener;
    }

    private OnCompletionListener onCompletionListener;

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        super.setOnCompletionListener(listener);
        this.onCompletionListener = listener;
    }

    public OnCompletionListener getOnCompletionListener() {
        return onCompletionListener;
    }

    private OnErrorListener onErrorListener;

    @Override
    public void setOnErrorListener(OnErrorListener listener) {
        super.setOnErrorListener(listener);
        this.onErrorListener = listener;
    }

    public OnErrorListener getOnErrorListener() {
        return onErrorListener;
    }

    private OnInfoListener onInfoListener;

    @Override
    public void setOnInfoListener(OnInfoListener listener) {
        super.setOnInfoListener(listener);
        this.onInfoListener = listener;
    }

    public OnInfoListener getOnInfoListener() {
        return onInfoListener;
    }

    @Override
    public void setOnPlayingBufferCacheListener(OnPlayingBufferCacheListener listener) {
        super.setOnPlayingBufferCacheListener(listener);
    }

    public void setMediaController(final ImethMediaController controller) {
        this.controller = controller;
        controller.videoView = this;

        final OnPreparedListener listener = onPreparedListener;

        setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (listener!= null) {
                    listener.onPrepared();
                }

                controller.onPrepared();
            }
        });
    }

    public void decodeSoftware() {
        setDecodeMode(DECODE_SW);
    }

    public void decodeHardware() {
        setDecodeMode(DECODE_HW);
    }

    public static String getErrorMessage(int code) {

        switch (code) {
            case MEDIA_ERROR_DISPLAY:
                return "SurfaceView for playback not created or occur an error";
            case MEDIA_ERROR_INVALID_INPUTFILE:
                return "the input video source is invalid";
            case MEDIA_ERROR_IO:
                return "File or network related operation errors.";
            case MEDIA_ERROR_MALFORMED:
                return "BitStream is not conforming to the related coding standard or file spec.";
            case MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                return "The video is streamed and its container is not valid for progressive playback i.e the video's index (e.g moov atom) is not at the start of the file.";
            case MEDIA_ERROR_NO_INPUTFILE:
                return "not set video source for playback";
            case MEDIA_ERROR_NO_SUPPORTED_CODEC:
                return "codec not supported the video source contains";
            case MEDIA_ERROR_SERVER_DIED:
                return "Media server died.";
            case MEDIA_ERROR_TIMED_OUT:
                return "Some operation takes too long to complete, usually more than 3-5 seconds.";
            case MEDIA_ERROR_UNSUPPORTED:
                return "BitStream is conforming to the related coding standard or file spec, but the media framework does not support the feature.";
            case MEDIA_INFO_BAD_INTERLEAVING:
                return "Bad interleaving means that a media has been improperly interleaved or not interleaved at all, e.g has all the video samples first then all the audio ones.";
            case MEDIA_INFO_BUFFERING_END:
                return "Player is resuming playback after filling buffers.";
            case MEDIA_INFO_BUFFERING_START:
                return "Player is temporarily pausing playback internally in order to buffer more data.";
            case MEDIA_INFO_NOT_SEEKABLE:
                return "The media cannot be seeked (e.g live stream)";
            case MEDIA_INFO_VIDEO_TRACK_LAGGING:
                return "The video is too complex for the decoder: it can't decode frames fast enough.";

            case MEDIA_ERROR_UNKNOWN:
            default:
                return "Unspecified player error.";
        }

    }
}

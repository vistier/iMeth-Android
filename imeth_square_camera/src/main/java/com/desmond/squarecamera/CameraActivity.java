package com.desmond.squarecamera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class CameraActivity extends Activity {

    public static final String INTENT_IS_SQUARE_PHOTO = "square";
    public static final String INTENT_IS_RESOLUTION_RATIO_X = "resolution.ratio.x";
    public static final String INTENT_IS_RESOLUTION_RATIO_Y = "resolution.ratio.y";

    public static enum ResolutionRatio {
        /** 3:4 分辨率 */
        ThreeFour(3, 4),
        /** 9:16 分辨率 */
        NineHex(9, 16);

        int width;
        int height;

        ResolutionRatio(int width, int height) {
            this.width = width;
            this.height = height;
        }

    }

    /**
     * @param activity
     * @param requestCode
     * @param isSquare    是否拍方形照片
     * @param resolutionRatio 像机获取相片分辨率比例
     * @see ResolutionRatio
     */
    public static void startActivityForRe(Activity activity, int requestCode, boolean isSquare, ResolutionRatio resolutionRatio) {
        Intent intent = new Intent(activity, CameraActivity.class);

        intent.putExtra(INTENT_IS_SQUARE_PHOTO, isSquare);
        intent.putExtra(INTENT_IS_RESOLUTION_RATIO_X, resolutionRatio.width);
        intent.putExtra(INTENT_IS_RESOLUTION_RATIO_Y, resolutionRatio.height);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForRe(Activity activity, int requestCode, boolean isSquare) {
        startActivityForRe(activity, requestCode, isSquare, ResolutionRatio.ThreeFour);
    }

    public static void startActivityForRe(Activity activity, int requestCode) {
        startActivityForRe(activity, requestCode, true);
    }

    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_camera);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance())
                    .commit();
        }
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void onCancel(View view) {
        getFragmentManager().popBackStack();
    }
}

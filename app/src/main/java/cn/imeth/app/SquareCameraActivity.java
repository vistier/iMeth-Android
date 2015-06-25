package cn.imeth.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;

public class SquareCameraActivity extends Activity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SquareCameraActivity.class);

        context.startActivity(intent);
    }

    private static final int REQUEST_CAMERA = 0;

    private Point mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.square_camera_activity);

        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CAMERA) {
            Uri photoUri = data.getData();
            // Get the bitmap in according to the width of the device
            Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(), mSize.x, mSize.x);
            ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void launchCamera(View view) {
        CameraActivity.startActivityForRe(this, REQUEST_CAMERA);
    }
}

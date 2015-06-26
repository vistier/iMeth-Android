package cn.imeth.app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.capricorn.ArcMenu;
import com.capricorn.RayMenu;

import org.json.JSONException;
import org.json.JSONObject;

import cn.imeth.android.activity.ImethActivity;
import cn.imeth.android.log.Log;
import cn.imeth.android.utils.Androids;
import cn.imeth.android.utils.TypefaceUtils;
import cn.imeth.android.view.DraggableFlagView;
import cn.imeth.app.image.slider.ImageSliderActivity;
import cn.imeth.video.play.VideoPlayActivity;


public class MainActivity extends ImethActivity {

    private static final int[] ITEM_DRAWABLES = {
            R.drawable.composer_camera,
            R.drawable.composer_music,
            R.drawable.composer_place,
            R.drawable.composer_sleep,
            R.drawable.composer_thought,
            R.drawable.composer_with };

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        final DraggableFlagView draggableFlagView = (DraggableFlagView) findViewById(R.id.flag_view);

        draggableFlagView.setOnDraggableFlagViewListener(new DraggableFlagView.OnDraggableFlagViewListener() {
            @Override
            public void onFlagDismiss(DraggableFlagView view) {
                Androids.makeText("我擦!这么给力!");
            }
        });

        draggableFlagView.setText("7");

        findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draggableFlagView.show("88");
            }
        });

        final TextView fontTextView = (TextView) findViewById(R.id.font);

        fontTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = getString(R.string.fa_android) + " "
                        + getString(R.string.fa_anchor) + " "
                        + getString(R.string.fa_facebook) + " "
                        + getString(R.string.fa_yahoo) + " "
                        + getString(R.string.fa_google_plus) + " "
                        + getString(R.string.fa_google_wallet);

                fontTextView.setText(text);
                fontTextView.setTextColor(Color.RED);
                TypefaceUtils.setFontAwesome(fontTextView);

                String url = "http://pl.youku.com/playlist/m3u8?vid=315461339&type=mp4&ts=1434700493&keyframe=0&ep=eiaXHk6NVsoJ5yvejT8bYynkIXFcXP0L8xuDgNFhCdQiS%2Bq6&sid=843470049281512456e6c&token=6325&ctype=12&ev=1&oip=2016043919";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/mp4");
                startActivity(intent);
            }
        });

        findViewById(R.id.ImageSliderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSliderActivity.startActivity(MainActivity.this);
            }
        });

        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
        ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);

        initArcMenu(arcMenu, ITEM_DRAWABLES);
        initArcMenu(arcMenu2, ITEM_DRAWABLES);

        RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            rayMenu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });// Add a menu item
        }

        findViewById(R.id.video_play_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPlayActivity.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.camera_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraActivity.startActivity(MainActivity.this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();

                    JSONObject params = new JSONObject();

                    try {
                        params = new JSONObject("{\"userID\":\"chenchaowen\",\"pageSize\":5,\"lastUpdateStamp\":\"1433407267\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest("http://demo.tradeicloud.com/QC/get/getRemarkList", params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("imeth", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.i("imeth", error.getMessage());
                        }
                    });

                    requestQueue.add(request);

                }
            });
        }
    }

}

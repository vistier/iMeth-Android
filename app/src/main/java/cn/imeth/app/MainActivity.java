package cn.imeth.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.imeth.android.activity.ImethActivity;
import cn.imeth.android.utils.Androids;
import cn.imeth.android.utils.TypefaceUtils;
import cn.imeth.android.view.DraggableFlagView;
import cn.imeth.app.image.slider.ImageSliderActivity;


public class MainActivity extends ImethActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        });

        findViewById(R.id.ImageSliderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSliderActivity.startActivity(MainActivity.this);
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

}

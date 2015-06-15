package cn.imeth.android.image.browse;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.imeth.android.R;
import cn.imeth.android.utils.ImageLoaderUtils;
import cn.imeth.android.utils.TypefaceUtils;

/**
 * Created by 火蚁 on 15/4/29.
 */
public class PhotoBrowseActivity extends Activity implements View.OnClickListener {

    public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
    private static final String BUNDLE_KEY_INDEX = "bundle_key_index";

    ViewPager viewpager;
    TextView tvPhotoIndex;
    TextView tvDownloadIcon;

    private PhotoAdapter adapter;

    private String[] imageUrls;

    private int index;

    public static void startActivity(Context context, int index, String... images) {

        index = index < 0 ? 0 : index;
        index = index >= images.length ? images.length : index;

        Intent intent = new Intent();
        intent.setClass(context, PhotoBrowseActivity.class);
        intent.putExtra(BUNDLE_KEY_INDEX, index);
        intent.putExtra(BUNDLE_KEY_IMAGES, images);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_browse_activity);

        initView();
    }

    private void initView() {

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tvPhotoIndex = (TextView) findViewById(R.id.tv_photo_index);
        tvDownloadIcon = (TextView) findViewById(R.id.tv_icon);

        setOnClickListener(R.id.ll_download);
        setOnClickListener(R.id.tv_photo_index);

        adapter = new PhotoAdapter(getFragmentManager());

        viewpager.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null) {
            index = intent.getIntExtra(BUNDLE_KEY_INDEX, 1);
            imageUrls = intent.getStringArrayExtra(BUNDLE_KEY_IMAGES);
        }
        setIndex();
        adapter.add(imageUrls);
        viewpager.setCurrentItem(index);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                setIndex();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TypefaceUtils.setSemantic(tvDownloadIcon);
    }

    private void setIndex() {
        tvPhotoIndex.setText((index + 1) + "/" + imageUrls.length);
    }

    private void setOnClickListener(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoBrowseActivity.this.onClick(v);
            }
        });
    }

    public void onClick(View v) {
        saveImageToGallery();
    }

    class PhotoAdapter extends FragmentStatePagerAdapter {

        private List<PhotoFragment> fragments;

        public PhotoAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        @Override
        public PhotoFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void add(String[] imageUrls) {
            for (String url : imageUrls) {
                fragments.add(PhotoFragment.newInstance(url));
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 保存图片到手机中
     * <p/>
     * update: 1.2014-05-06 下午 15：35
     * 更新内容: 修改图片下载完成之后再保存到图库中
     */
    public void saveImageToGallery() {
        final Bitmap bitmap = ImageLoader.getInstance().loadImageSync(imageUrls[index], ImageLoaderUtils.getOption());
        ImageLoader.getInstance().loadImage(imageUrls[index], ImageLoaderUtils.getOption(), new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                saveImageToGallery(PhotoBrowseActivity.this, bitmap);
            }
        });
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "/mocoop/image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

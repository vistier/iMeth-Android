package cn.imeth.android.image.choose;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.imeth.android.image.R;
import cn.imeth.android.image.browse.PhotoBrowseActivity;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.lang.ImethLangActivity;
import cn.imeth.android.lang.utils.Androids;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoChooseActivity extends ImethLangActivity implements PhotoFolderListPopupWindow.OnPhotoFolderSelectListener {

    public static final String INTENT_MAX_SELECT_NUM = "max_num";

    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PhotoChooseActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity activity, int requestCode, int maxSelectNumber) {
        Intent intent = new Intent(activity, PhotoChooseActivity.class);
        intent.putExtra(INTENT_MAX_SELECT_NUM, maxSelectNumber);

        activity.startActivityForResult(intent, requestCode);
    }

    //private List<String> photos;
    private GridView gridView;
    private PhotoAdapter adapter;

    /**
     * 底部工具栏
     */
    private View toolBar;
    /**
     * 当前文件夹
     */
    private TextView folderValue;
    /**
     * 当前文件数量
     */
    private TextView countValue;

    private Button okBtn;

    private PhotoFolderListPopupWindow photoFolderListPopupWindow;
    private ProgressDialog progressDialog;

    //private File photoFolder;

    /**
     * 最多选择图片数量
     */
    int maxSelectNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_choose_activity);

        if (!ImageLoaderUtils.isInited()) {
            ImageLoaderUtils.init(this);
        }

        initIntent();

        adapter = new PhotoAdapter(this, "", maxSelectNum);

        getImages();
        initViews();
        initEvent();

    }

    private void initIntent() {
        maxSelectNum = getIntent().getIntExtra(INTENT_MAX_SELECT_NUM, maxSelectNum);
        if (maxSelectNum <= 0) {
            maxSelectNum = -1;
        }

    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void getImages() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Androids.makeText("暂无外部存储").show();
            return;
        }

        progressDialog = ProgressDialog.show(this, null, "正在加载中...");

        new AsyncTask<Void, Void, List<PhotoFolder>>() {

            @Override
            protected List<PhotoFolder> doInBackground(Void... params) {
                Log.v("imeth", "doInBackground");
                return ContentResolvers.queryPhoto(getApplicationContext());
            }

            @Override
            protected void onPostExecute(List<PhotoFolder> folders) {
                Log.v("imeth", "onPostExecute" + folders.size());
                if(!folders.isEmpty()) {
                    photoFolderListPopupWindow.setData(folders);

                    onSelected(folders.get(0));
                } else {

                }
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void initViews() {
        gridView = (GridView) findViewById(R.id.grid_view);

        folderValue = (TextView) findViewById(R.id.folder_value);
        countValue = (TextView) findViewById(R.id.count_value);
        okBtn = (Button) findViewById(R.id.ok_btn);

        toolBar = findViewById(R.id.tool_bar);

        initPopupWindow();
    }

    private void initEvent() {
        toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photoFolderListPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                photoFolderListPopupWindow.showAsDropDown(toolBar, 0, 0);

                setWindowAlpha(.3f);
            }
        });
    }

    private void initPopupWindow() {
        photoFolderListPopupWindow = new PhotoFolderListPopupWindow(
                LayoutInflater.from(this).inflate(R.layout.photo_folder_list_popup_window_layout, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (Androids.Display.height * 0.7),
                true);

        photoFolderListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });

        photoFolderListPopupWindow.setListener(this);

    }

    @Override
    public void onSelected(final PhotoFolder folder) {
        final File photoFolder = new File(folder.dir);
        List<String> photos = Arrays.asList(photoFolder.list(ContentResolvers.imageFileFilter));

        // 排序图片
        Collections.sort(photos, new Comparator<String>() {

            @Override
            public int compare(String lhs, String rhs) {
                File lFile = new File(photoFolder, lhs);
                File rFile = new File(photoFolder, rhs);

                return ((Long) rFile.lastModified()).compareTo(lFile.lastModified());
            }
        });

        //adapter = new PhotoAdapter(this, folder.dir, maxSelectNum);
        adapter.folder = folder.dir;
        adapter.reset(photos);
        //adapter.addAll(photos);
        adapter.listener = onPhotoClickListener;
        gridView.setAdapter(adapter);

        countValue.setText(folder.count + "张");
        folderValue.setText(folder.name);
        photoFolderListPopupWindow.adapter.currSelectedPhoneFolder = folder;
        photoFolderListPopupWindow.dismiss();

    }

    private PhotoAdapter.OnPhotoClickListener onPhotoClickListener = new PhotoAdapter.OnPhotoClickListener() {

        @Override
        public void onPhotoClick(String photo) {
            photo = "file://" + photo;

            List<String> items = adapter.getItems();
            List<String> photos = new ArrayList<String>();

            for (String p : items) {
                photos.add("file://" + adapter.folder + File.separator + p);
            }

            PhotoBrowseActivity.startActivity(PhotoChooseActivity.this, false, photos.indexOf(photo), photos.toArray(new String[0]));
        }

        @Override
        public void onChangeSelectedNum(int num) {
            okBtn.setText(String.format("确定[%s]", num));
        }
    };

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed() {
        if (adapter.selectedPhotos.isEmpty()) {
            super.onBackPressed();
        } else {
            onResult(null);
        }
    }

    public void onResult(View view) {
        Intent intent = new Intent();
        intent.putExtra("photos", adapter.selectedPhotos.toArray(new String[0]));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static List<String> parseIntent(Intent intent) {
        return Arrays.asList(intent.getStringArrayExtra("photos"));
    }

}

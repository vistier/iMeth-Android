package cn.imeth.android.image.choose;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PhotoChooseActivity.class));
    }

    private List<String> photos;
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

    private PhotoFolderListPopupWindow photoFolderListPopupWindow;
    private ProgressDialog progressDialog;

    private List<PhotoFolder> photoFolders = new ArrayList<>();
    private File photoFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_choose_activity);

        if(!ImageLoaderUtils.isInited()) {
            ImageLoaderUtils.init(this);
        }

        getImages();
        initViews();
        initEvent();

    }

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
                //photoFolders = folders;

                photoFolders.clear();
                photoFolders.addAll(folders);

                onSelected(photoFolders.get(0));
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void initViews() {
        gridView = (GridView) findViewById(R.id.grid_view);

        folderValue = (TextView) findViewById(R.id.folder_value);
        countValue = (TextView) findViewById(R.id.count_value);

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
                true,
                photoFolders);

        photoFolderListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });

        photoFolderListPopupWindow.setListener(this);

    }

    @Override
    public void onSelected(PhotoFolder folder) {
        photoFolder = new File(folder.dir);
        photos = Arrays.asList(photoFolder.list(ContentResolvers.imageFileFilter));

        adapter = new PhotoAdapter(this, photos, folder.dir);
        adapter.listener = onPhotoClickListener;
        gridView.setAdapter(adapter);

        countValue.setText(folder.count + "张");
        folderValue.setText(folder.name);

        photoFolderListPopupWindow.dismiss();

    }

    private PhotoAdapter.OnPhotoClickListener onPhotoClickListener = new PhotoAdapter.OnPhotoClickListener() {
        @Override
        public void onPhotoClick(String photo) {
            PhotoBrowseActivity.startActivity(PhotoChooseActivity.this, 0, photo);
        }
    };

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed() {
        onResult();
        super.onBackPressed();
    }

    public void onResult(){
        Intent intent = new Intent();
        intent.putExtra("photos", adapter.selectedPhotos.toArray());
        setResult(Activity.RESULT_OK);
    }

    public static List<String> parseIntent(Intent intent){
        return Arrays.asList(intent.getStringArrayExtra("photos"));
    }

}

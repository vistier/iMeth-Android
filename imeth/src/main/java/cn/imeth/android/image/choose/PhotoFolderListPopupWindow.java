package cn.imeth.android.image.choose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.imeth.android.R;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoFolderListPopupWindow extends PopupWindow {

    protected List<PhotoFolder> folders;

    protected ListView folderListView;
    protected PhotoFolderItemAdapter adapter;

    protected OnPhotoFolderSelectListener listener;

    public PhotoFolderListPopupWindow(View contentView, int width, int height, boolean focusable) {
        this(contentView, width, height, focusable, new ArrayList<PhotoFolder>(0));
    }

    public PhotoFolderListPopupWindow(View contentView, int width, int height, boolean focusable, List<PhotoFolder> folders) {
        super(contentView, width, height, focusable);

        if (folders != null) {
            this.folders = folders;
            this.adapter = new PhotoFolderItemAdapter(contentView.getContext(), folders);
        } else {
            throw new RuntimeException("image folder is null");
        }

        setBackgroundDrawable(new BitmapDrawable(null, (Bitmap) null));
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }

                return false;
            }
        });

        initViews();
        initEvents();
        init();
    }

    public void initViews() {
        folderListView = (ListView) findViewById(R.id.list_view);
        folderListView.setAdapter(adapter);
    }

    public void initEvents() {
        folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelected(folders.get(position));
                }
            }
        });
    }

    public void init() {

    }

    public void setListener(OnPhotoFolderSelectListener listener) {
        this.listener = listener;
    }

    protected View findViewById(int id) {
        return getContentView().findViewById(id);
    }

    protected static int dp2Px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static interface OnPhotoFolderSelectListener {
        void onSelected(PhotoFolder folder);
    }

}

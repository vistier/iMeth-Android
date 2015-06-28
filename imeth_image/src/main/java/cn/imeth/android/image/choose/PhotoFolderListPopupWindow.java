package cn.imeth.android.image.choose;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

import cn.imeth.android.image.R;


/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoFolderListPopupWindow extends PopupWindow {


    protected ListView folderListView;
    protected PhotoFolderItemAdapter adapter;

    protected OnPhotoFolderSelectListener listener;

    public PhotoFolderListPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);

        this.adapter = new PhotoFolderItemAdapter(contentView.getContext());

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

    public void setData(List<PhotoFolder> folders) {
        adapter.reset(folders);
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
                    listener.onSelected(adapter.getItem(position));
                }
            }
        });
    }

    public void init() {

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        adapter.notifyDataSetChanged();
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

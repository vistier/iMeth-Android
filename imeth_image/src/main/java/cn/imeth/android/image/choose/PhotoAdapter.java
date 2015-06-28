package cn.imeth.android.image.choose;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.imeth.android.image.R;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.lang.adapter.ViewHolderArrayAdapter;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoAdapter extends ViewHolderArrayAdapter<PhotoAdapter.PhotoViewHolder, String> {

    private static int SELECTED_COLOR_FILTER = Color.parseColor("#77000000");

    ImageLoader imageLoader;
    List<String> selectedPhotos = new LinkedList<>();

    String folder;

    OnPhotoClickListener listener;

    public PhotoAdapter(Context context, List<String> phones, String folder) {
        super(context, R.layout.photo_adapter);
        imageLoader = ImageLoader.getInstance();
        this.folder = folder;

        addAll(phones);
    }

    @Override
    protected PhotoViewHolder initViewHolder(View view, int position) {
        final PhotoViewHolder holder = new PhotoViewHolder();

        holder.photoImg = (ImageView) view.findViewById(R.id.photo_img);
        holder.selectIc = (ImageButton) view.findViewById(R.id.select_ic);

        holder.selectIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String photo = getItem(holder.getPosition());

                if (selectedPhotos.contains(photo)) {
                    selectedPhotos.remove(photo);

                    holder.selectIc.setImageResource(R.mipmap.photo_unselected);
                    holder.photoImg.setColorFilter(null);

                } else {
                    selectedPhotos.add(photo);

                    holder.selectIc.setImageResource(R.mipmap.photo_selected);
                    holder.photoImg.setColorFilter(SELECTED_COLOR_FILTER);

                }
            }
        });

        holder.photoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    String path = "file://" + folder + File.separator + getItem(holder.getPosition());
                    listener.onPhotoClick(path);
                }

            }
        });

        return holder;
    }

    @Override
    protected void fillViewHolder(PhotoViewHolder holder, String photo, int position) {

        boolean isSelected = isSelected(photo);

        String path = "file://" + folder + File.separator + photo;

        holder.photoImg.setColorFilter(isSelected ? SELECTED_COLOR_FILTER : Color.TRANSPARENT);
        imageLoader.displayImage(path, holder.photoImg, ImageLoaderUtils.getOption(), imageLoadingListener);

        holder.selectIc.setImageResource(isSelected ? R.mipmap.photo_selected : R.mipmap.photo_unselected);

    }

    ImageLoadingListener imageLoadingListener = new SimpleImageLoadingListener() {
        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            ((ImageView) view).setImageResource(R.mipmap.photo_no);
        }
    };

    static class PhotoViewHolder extends ViewHolderArrayAdapter.ViewHolder {
        ImageView photoImg;
        ImageButton selectIc;
    }

    private boolean isSelected(String key) {
        return selectedPhotos.contains(key);
    }

    public static interface OnPhotoClickListener {

        void onPhotoClick(String photo);

    }

}

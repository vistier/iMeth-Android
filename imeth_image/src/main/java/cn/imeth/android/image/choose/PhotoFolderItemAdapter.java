package cn.imeth.android.image.choose;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.imeth.android.image.R;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.lang.adapter.ViewHolderArrayAdapter;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoFolderItemAdapter extends ViewHolderArrayAdapter<PhotoFolderItemAdapter.PhotoFolderViewHolder, PhotoFolder> {

    private ImageLoader imageLoader;

    public PhotoFolderItemAdapter(Context context, List<PhotoFolder> data) {
        super(context, R.layout.photo_folder_list_item_adapter);
        imageLoader = ImageLoader.getInstance();

        addAll(data);
    }

    @Override
    protected PhotoFolderViewHolder initViewHolder(View view, int position) {
        PhotoFolderViewHolder holder = new PhotoFolderViewHolder();

        // 找出view
        holder.folderImg = (ImageView) view.findViewById(R.id.folder_img);
        holder.nameValue = (TextView) view.findViewById(R.id.name_value);
        holder.countValue = (TextView) view.findViewById(R.id.count_value);

        return holder;
    }

    @Override
    protected void fillViewHolder(PhotoFolderViewHolder holder,PhotoFolder folder, int position) {

        // 设置view
        holder.nameValue.setText(folder.name);
        holder.countValue.setText(folder.count + "张");

        imageLoader.displayImage("file://"+folder.firstPhotoPath, holder.folderImg, ImageLoaderUtils.getOption());

    }

    static class PhotoFolderViewHolder extends ViewHolderArrayAdapter.ViewHolder {
        TextView nameValue;
        TextView countValue;
        ImageView folderImg;
    }

}

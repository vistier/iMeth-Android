package cn.imeth.android.image.choose;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.imeth.android.image.R;
import cn.imeth.android.image.utils.ImageLoaderUtils;
import cn.imeth.android.lang.adapter.ViewHolderArrayAdapter;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoFolderItemAdapter extends ViewHolderArrayAdapter<PhotoFolderItemAdapter.PhotoFolderViewHolder, PhotoFolder> {

    private ImageLoader imageLoader;

    PhotoFolder currSelectedPhoneFolder;

    public PhotoFolderItemAdapter(Context context) {
        super(context, R.layout.photo_folder_list_item_adapter);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    protected PhotoFolderViewHolder initViewHolder(View view, int position) {
        PhotoFolderViewHolder holder = new PhotoFolderViewHolder();

        // 找出view
        holder.folderImg = (ImageView) view.findViewById(R.id.folder_img);
        holder.nameValue = (TextView) view.findViewById(R.id.name_value);
        holder.countValue = (TextView) view.findViewById(R.id.count_value);
        holder.selectedIc = view.findViewById(R.id.selected_ic);

        return holder;
    }

    @Override
    protected void fillViewHolder(PhotoFolderViewHolder holder, PhotoFolder folder, int position) {

        // 设置view
        holder.nameValue.setText(folder.name);
        holder.countValue.setText(folder.count + "张");

        imageLoader.displayImage("file://" + folder.firstPhotoPath, holder.folderImg, ImageLoaderUtils.getOption());

        if(currSelectedPhoneFolder!=null)
        holder.selectedIc.setVisibility(folder.equals(currSelectedPhoneFolder) ? View.VISIBLE : View.INVISIBLE);

    }

    static class PhotoFolderViewHolder extends ViewHolderArrayAdapter.ViewHolder {
        TextView nameValue;
        TextView countValue;
        ImageView folderImg;
        View selectedIc;
    }

}

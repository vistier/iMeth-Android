package cn.imeth.android.image.browse;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.imeth.android.R;
import cn.imeth.android.utils.Androids;
import cn.imeth.android.utils.ImageLoaderUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 火蚁 on 15/4/29.
 */
public class PhotoFragment extends Fragment {

    PhotoView image;
    ProgressBar loading;
    private String imageUrl;

    private PhotoViewAttacher attacher;

    public static PhotoFragment newInstance(String imageUrl) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.photo_item_layout, container, false);

        image = (PhotoView) root.findViewById(R.id.image);
        loading = (ProgressBar) root.findViewById(R.id.loading);

        loadImage();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            imageUrl = args.getString("url");
        }
    }

    protected void loadImage() {
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(imageUrl, image, ImageLoaderUtils.getOption(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    if (loading != null) {
                        loading.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    if (loading != null) {
                        loading.setVisibility(View.GONE);
                    }
                    Androids.makeText("加载图片失败");
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (loading != null) {
                        loading.setVisibility(View.GONE);
                        FadeInBitmapDisplayer.animate(image, 1000);
                    }
                    if (image != null) {
                        attacher = new PhotoViewAttacher(image);
                        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                            @Override
                            public void onPhotoTap(View view, float v, float v2) {
                                getActivity().finish();
                            }
                        });
                        attacher.update();
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
    }

}

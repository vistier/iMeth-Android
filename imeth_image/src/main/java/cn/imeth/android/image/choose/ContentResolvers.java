package cn.imeth.android.image.choose;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class ContentResolvers {

    public static List<PhotoFolder> queryPhoto(Context context) {
        Map<String,PhotoFolder> folders = new HashMap<>();
        HashSet<String> completeFolders = new HashSet<>();

        Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        // 只查询jpeg和png图片
        Cursor cursor = resolver.query(photoUri,
                null,
                "",//MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{},
                MediaStore.Images.Media.DATE_MODIFIED);

        String photoPath = null;
        long photoLastModifiedTime = 0;

        while (cursor.moveToNext()) {
            // 图片路径
            photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            photoLastModifiedTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));

            File folder = new File(photoPath).getParentFile();
            if (folder == null) {
                continue;
            }

            String folderPath = folder.getAbsolutePath();
            if (completeFolders.contains(folderPath)) {

                PhotoFolder photoFolder = folders.get(folderPath);
                long time = photoFolder.lastModifiedTime;

                if(time < photoLastModifiedTime) {
                    photoFolder.lastModifiedTime = photoLastModifiedTime;
                    photoFolder.firstPhotoPath = photoPath;
                }

                continue;
            }

            completeFolders.add(folderPath);

            PhotoFolder photoFolder = new PhotoFolder();
            photoFolder.name = folder.getName();
            photoFolder.dir = folderPath;
            photoFolder.firstPhotoPath = photoPath;
            //photoFolder.lastModifiedTime = folder.lastModified();
            photoFolder.lastModifiedTime = photoLastModifiedTime;

            int count = folder.list(imageFileFilter).length;
            photoFolder.count = count;

            folders.put(folderPath, photoFolder);
        }

        cursor.close();

        List<PhotoFolder> data = new ArrayList<>(folders.values());

        Collections.sort(data, new Comparator<PhotoFolder>() {

            @Override
            public int compare(PhotoFolder arg0, PhotoFolder arg1) {
                return ((Long) arg1.lastModifiedTime).compareTo(arg0.lastModifiedTime);
            }
        });

        return data;
    }

    static FilenameFilter imageFileFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg");
        }
    };


}

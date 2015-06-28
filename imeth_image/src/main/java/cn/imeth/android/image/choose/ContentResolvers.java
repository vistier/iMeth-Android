package cn.imeth.android.image.choose;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class ContentResolvers {

    public static List<PhotoFolder> queryPhoto(Context context) {
        List<PhotoFolder> folders = new ArrayList<>();
        HashSet<String> completeFolders = new HashSet<>();
        String firstPhoto = null;

        Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = context.getContentResolver();

        // 只查询jpeg和png图片
        Cursor cursor = resolver.query(photoUri,
                null,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpge", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        while (cursor.moveToNext()) {
            // 图片路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            if (firstPhoto == null) {
                firstPhoto = path;
            }

            File folder = new File(path).getParentFile();
            if (folder == null) {
                continue;
            }

            String folderPath = folder.getAbsolutePath();
            if (completeFolders.contains(folderPath)) {
                continue;
            }

            completeFolders.add(folderPath);

            PhotoFolder photoFolder;
            photoFolder = new PhotoFolder();
            photoFolder.name = folder.getName();
            photoFolder.dir = folderPath;
            photoFolder.firstPhotoPath = firstPhoto;
            photoFolder.lastModifiedTime = folder.lastModified();

            int count = folder.list(imageFileFilter).length;
            photoFolder.count = count;

            folders.add(photoFolder);
        }

        cursor.close();

        return folders;
    }

    static FilenameFilter imageFileFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg");
        }
    };


}

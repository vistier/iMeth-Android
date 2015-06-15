package cn.imeth.android.vollery;

import java.io.File;
import java.util.Map;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/13.
 */
public interface MultiPartRequest {

    void addFileUpload(String param, File file);

    void addStringUpload(String param, String content);

    Map<String, File> getFileUploads();

    Map<String, String> getStringUploads();
}

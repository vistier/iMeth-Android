package cn.imeth.android.vollery;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 建议不要下载过大的文件
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/17.
 */
public class FileRequest extends Request<File> {

    String saveFilePath;
    Response.Listener<File> listener;

    /**
     *
     * @param method
     * @param url
     * @param saveFilePath
     * @param listener
     * @param errorListener
     */
    public FileRequest(int method, String url, String saveFilePath, Response.Listener<File> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.saveFilePath = saveFilePath;
        this.listener = listener;
    }

    @Override
    protected Response<File> parseNetworkResponse(NetworkResponse response) {
        File file = getFile(response.data,saveFilePath);
        return Response.success(file, null);
    }

    @Override
    protected void deliverResponse(File response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    public static File getFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return file;
    }


}

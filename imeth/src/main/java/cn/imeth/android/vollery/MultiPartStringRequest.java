package cn.imeth.android.vollery;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/13.
 */
public class MultiPartStringRequest extends Request<String> implements MultiPartRequest {

    private final Response.Listener<String> mListener;
    /* To hold the parameter name and the File to upload */
    private Map<String, File> fileUploads = new HashMap<String, File>();

    /* To hold the parameter name and the string content to upload */
    private Map<String, String> stringUploads = new HashMap<String, String>();

    public MultiPartStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    @Override
    public void addFileUpload(String param, File file) {
        fileUploads.put(param, file);
    }

    @Override
    public void addStringUpload(String param, String content) {
        stringUploads.put(param, content);
    }

    @Override
    public Map<String, File> getFileUploads() {
        return fileUploads;
    }

    @Override
    public Map<String, String> getStringUploads() {
        return stringUploads;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }
}

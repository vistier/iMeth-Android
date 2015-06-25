package cn.imeth.android.vollery;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import java.io.File;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/17.
 */
public class FileJsonRequest extends JsonRequest<File> {

    String saveFilePath;

    public FileJsonRequest(int method, String url, String requestBody, String saveFilePath, Response.Listener<File> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        this.saveFilePath = saveFilePath;
    }

    @Override
    protected Response<File> parseNetworkResponse(NetworkResponse response) {
        File file = FileRequest.getFile(response.data,saveFilePath);
        return Response.success(file, null);
    }
}

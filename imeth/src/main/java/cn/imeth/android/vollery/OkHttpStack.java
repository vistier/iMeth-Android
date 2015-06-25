package cn.imeth.android.vollery;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/18.
 */
public class OkHttpStack implements HttpStack {

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {

        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder()
                .url(request.getUrl());

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.putAll(request.getHeaders());
        headers.putAll(additionalHeaders);

        setHeaders(builder, headers);



        return null;
    }

    private void setHeaders(com.squareup.okhttp.Request.Builder builder, Map<String,String> headers) {
        if (headers != null && !headers.isEmpty()){
            for (String name : headers.keySet()) {
                builder.header(name, headers.get(name));
            }
         }
    }

    private void setMethod(com.squareup.okhttp.Request.Builder builder, Request<?> request) {

        switch (request.getMethod()) {
            case Request.Method.DEPRECATED_GET_OR_POST:
//                RequestBody
                break;
            case Request.Method.GET:
                break;
            case Request.Method.DELETE:
                break;
            case Request.Method.POST:
                break;
            case Request.Method.PUT:
                break;
            case Request.Method.HEAD:
                break;
            case Request.Method.OPTIONS:
                break;
            case Request.Method.TRACE:
                break;
            case Request.Method.PATCH:
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }

    }

}

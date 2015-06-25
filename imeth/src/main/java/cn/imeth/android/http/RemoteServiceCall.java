package cn.imeth.android.http;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by 戴文龙 on 2014/12/29.
 */
public interface RemoteServiceCall {

    InputStream post(String url, Map<String, String> params);

    InputStream postOverSSL(String url, Map<String, String> params);

    InputStream postWithAttachment(String url, Map<String, Object> attachments);

    InputStream postWithAttachment(String url, Map<String, String> params, Map<String, Object> attachments);

    InputStream postWithAttachmentOverSSL(String url, Map<String, Object> attachments);

    InputStream postWithAttachmentOverSSL(String url, Map<String, String> params, Map<String, Object> attachments);

    InputStream get(String url, Map<String, String> params);

    InputStream getOverSSL(String url, Map<String, String> params);


}

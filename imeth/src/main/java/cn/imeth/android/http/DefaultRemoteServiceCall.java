package cn.imeth.android.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.imeth.android.exception.RemoteServerException;
import cn.imeth.android.exception.RequestEncryptException;
import cn.imeth.android.exception.RequestParametersException;
import cn.imeth.android.exception.RequestTimeoutException;
import cn.imeth.android.log.Log;


/**
 * Created by 戴文龙 on 2014/12/29.
 */
public class DefaultRemoteServiceCall implements RemoteServiceCall {

    private static int timeout = 6000;
    public static CookieStore cookieStore;
    private Authenticator authenticator;


    @Override
    public InputStream post(String url, Map<String, String> params) {
        return post(url,params,false);
    }

    @Override
    public InputStream postOverSSL(String url, Map<String, String> params) {
        return post(url,params,true);
    }

    @Override
    public InputStream postWithAttachment(String url, Map<String, Object> attachments) {
        return post(url,null,attachments,false);
    }

    @Override
    public InputStream postWithAttachment(String url, Map<String, String> params, Map<String, Object> attachments) {
        return post(url,params,attachments,false);
    }

    @Override
    public InputStream postWithAttachmentOverSSL(String url, Map<String, Object> attachments) {
        return post(url,null,attachments,true);
    }

    @Override
    public InputStream postWithAttachmentOverSSL(String url, Map<String, String> params, Map<String, Object> attachments) {
        return post(url,params,attachments,true);
    }

    @Override
    public InputStream get(String url, Map<String, String> params) {
        return get(url,params,false);
    }

    @Override
    public InputStream getOverSSL(String url, Map<String, String> params) {
        return get(url,params,true);
    }

    private InputStream get(String url, Map<String, String> params, boolean isEncrypt) {

        try {
            url = buildQueryString(url, params, true);
        } catch (Exception e) {
            throw new RequestParametersException(e);
        }
        return request(new HttpGet(url), isEncrypt);
    }

    private InputStream post(String url,Map<String, String> params,boolean isEncrypt) {
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {

            ArrayList<BasicNameValuePair> entities = new ArrayList<>();

            Iterator<String> iterator = params.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                entities.add(new BasicNameValuePair(key,params.get(key)));
            }

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(null, HTTP.UTF_8));
            } catch (UnsupportedEncodingException e) {
                throw new RequestParametersException();
            }
        }

        return request(httpPost,isEncrypt);
    }

    private InputStream post(String url, Map<String, String> params, Map<String, Object> attachments, boolean isEncrypt) {

        if (attachments == null) {
            return post(url, params);
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        try {
            Iterator<String> iterator = params.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addTextBody(key, params.get(key));
            }

            iterator = attachments.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                Object attachment = attachments.get(key);

                if (attachment instanceof File) {
                    builder.addBinaryBody(key, ((File) attachment));
                } else if (attachment instanceof InputStream) {
                    builder.addBinaryBody(key, (InputStream) attachment);
                } else if (attachment instanceof byte[]) {
                    builder.addBinaryBody(key, (byte[]) attachment);
                } else {
                    throw new RequestParametersException();
                }
            }

        } catch (Exception e) {
            throw new RequestParametersException(e);
        }

        HttpPost post = new HttpPost(url);
        post.setEntity(builder.build());

        return request(post, isEncrypt);
    }

    private InputStream request(HttpUriRequest httpUriRequest, boolean isEncrypt) {

        try {
            preSendingRequest(httpUriRequest);

            DefaultHttpClient httpClient = getHttpClient(isEncrypt);
            HttpResponse response = httpClient.execute(httpUriRequest);

            responseReturned(response);

            cookieStore = httpClient.getCookieStore();
            int status = response.getStatusLine().getStatusCode();

            if (status != 200 && status != 300) {
                Log.d("imeth DefaultRemoteServiceCall","request status :" + status);
                throw new RemoteServerException("request status : " + status);
            }

            HttpEntity entity = response.getEntity();

            Log.d("imeth DefaultRemoteServiceCall", "Download content length: " + entity.getContentLength());

            return entity.getContent();

        } catch (ConnectTimeoutException e) {
            throw new RequestTimeoutException();
        } catch (RemoteServerException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteServerException(e);
        } finally {
            //httpUriRequest.abort();
        }

    }

    protected void preSendingRequest(HttpUriRequest httpUriRequest) {
    }

    protected void responseReturned(HttpResponse response) {

    }

    private DefaultHttpClient getHttpClient(boolean isEncrypt) {
        BasicHttpParams params = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(params, timeout);
        HttpConnectionParams.setSoTimeout(params, 10000);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        ThreadSafeClientConnManager connManager = null;

        if (isEncrypt) {
            try {
                KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
                store.load(null, null);
                AdSSlSocketFactory certificate = new AdSSlSocketFactory(store);

                certificate.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));

                connManager = new ThreadSafeClientConnManager(params, registry);

            } catch (Exception e) {
                throw new RequestEncryptException(e);
            }
        }

        DefaultHttpClient httpClient = new DefaultHttpClient(connManager, params);

        if (this.authenticator != null) {
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.authenticator.username, this.authenticator.password));
            httpClient.setCredentialsProvider(provider);
        }

        if (cookieStore != null) {
            httpClient.setCookieStore(cookieStore);
        }

        return httpClient;
    }

    public void setConnectionTimeout(int timeout) {
        DefaultRemoteServiceCall.timeout = timeout;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    private static class AdSSlSocketFactory extends SSLSocketFactory {

        SSLContext context = SSLContext.getInstance("TLS");

        public AdSSlSocketFactory(KeyStore store) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(store);
            this.context.init(null, new TrustManager[]{new AdX509TrystManager()}, null);
        }

        @Override
        public Socket createSocket() throws IOException {
            return super.createSocket();
        }

        public class AdX509TrystManager implements X509TrustManager {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }

    }

    public static String buildQueryString(String url, Map<String, String> params, boolean needEncode) throws UnsupportedEncodingException {

        if (params != null) {
            StringBuffer buffer = new StringBuffer();
            Iterator iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String str = (String) iterator.next();
                if (buffer.length() > 0) {
                    buffer.append("&");
                }

                if (needEncode) {
                    buffer.append(str + "=" + URLEncoder.encode((String) params.get(str), "utf-8"));
                } else {
                    buffer.append(str + "=" + (String) params.get(str));
                }
            }
            if (buffer.length() > 0) {
                url = url + "?";
                url = url + buffer.toString();
            }
        }


        return url;
    }

}

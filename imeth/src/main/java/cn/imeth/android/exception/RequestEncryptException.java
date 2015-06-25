package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/18.
 */
public class RequestEncryptException extends RemoteServerException {

    public RequestEncryptException() {
    }

    public RequestEncryptException(String detailMessage) {
        super(detailMessage);
    }

    public RequestEncryptException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RequestEncryptException(Throwable throwable) {
        super(throwable);
    }
}

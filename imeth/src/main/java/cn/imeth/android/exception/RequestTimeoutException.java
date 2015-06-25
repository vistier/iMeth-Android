package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/18.
 */
public class RequestTimeoutException extends RemoteServerException {

    public RequestTimeoutException() {
    }

    public RequestTimeoutException(String detailMessage) {
        super(detailMessage);
    }

    public RequestTimeoutException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RequestTimeoutException(Throwable throwable) {
        super(throwable);
    }
}

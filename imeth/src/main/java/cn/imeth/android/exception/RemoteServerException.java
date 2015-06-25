package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/18.
 */
public class RemoteServerException extends SystemException {

    public RemoteServerException() {
    }

    public RemoteServerException(String detailMessage) {
        super(detailMessage);
    }

    public RemoteServerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RemoteServerException(Throwable throwable) {
        super(throwable);
    }
}

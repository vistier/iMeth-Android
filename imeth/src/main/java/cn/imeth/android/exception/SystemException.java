package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class SystemException extends RuntimeException {

    public SystemException() {
    }

    public SystemException(String detailMessage) {
        super(detailMessage);
    }

    public SystemException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SystemException(Throwable throwable) {
        super(throwable);
    }
}

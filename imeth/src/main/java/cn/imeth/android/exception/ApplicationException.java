package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
    }

    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }

    public ApplicationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }
}

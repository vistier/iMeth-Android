package cn.imeth.android.exception;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/18.
 */
public class RequestParametersException extends RemoteServerException {

    public RequestParametersException() {
    }

    public RequestParametersException(String detailMessage) {
        super(detailMessage);
    }

    public RequestParametersException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RequestParametersException(Throwable throwable) {
        super(throwable);
    }
}

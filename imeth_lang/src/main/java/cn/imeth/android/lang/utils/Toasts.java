package cn.imeth.android.lang.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 戴文龙(daiwenlong@imeth.cn) on 2015/4/28.
 */
public class Toasts {

    public Toast toast;

    public Toast makeText(Context context, CharSequence message){

        if(toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();

        return toast;
    }

    public void close() {
        toast = null;
    }
}

package cn.imeth.android.lang.utils;

import android.view.View;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/7/22.
 */
public class Views {

    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

}

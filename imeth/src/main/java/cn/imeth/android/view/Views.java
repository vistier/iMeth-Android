package cn.imeth.android.view;

import android.view.View;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/13.
 */
public class Views {

    // 通过一个viewId来获取一个view
    public static <T extends View> T findViewById(View container, int viewId) {
        return (T)container.findViewById(viewId);
    }

}

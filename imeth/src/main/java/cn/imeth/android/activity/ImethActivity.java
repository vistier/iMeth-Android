package cn.imeth.android.activity;

import android.app.Activity;

import cn.imeth.android.control.QuitPromptControl;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class ImethActivity extends Activity {

    /** 再按一次退出控制器 */
    private QuitPromptControl quitPromptControl;

    protected void supportQuitPrompt() {
        quitPromptControl = new QuitPromptControl();
    }

    @Override
    public void onBackPressed() {
        if (quitPromptControl!=null && quitPromptControl.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }
}

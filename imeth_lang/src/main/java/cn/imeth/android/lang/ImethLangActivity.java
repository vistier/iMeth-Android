package cn.imeth.android.lang;

import android.app.Activity;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class ImethLangActivity extends Activity {

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

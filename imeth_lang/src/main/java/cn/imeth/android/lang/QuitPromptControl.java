package cn.imeth.android.lang;

import cn.imeth.android.lang.utils.Androids;

/**
 * <h1>再按一次退出控制器</h1>
 *
 * <p>
 *     使用例子:
 *
 * private QuitPromptControl quitPromptControl = new QuitPromptControl();
 * public void onBackPressed() {
 *    if (quitPromptControl.onBackPressed()) {
 *       super.onBackPressed();
 *   }
 * }
 *
 *
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class QuitPromptControl {

    public static long MISTIMING = 3000L;
    public String quitPrompt = "再按一次退出";

    private long lastShowQuitPromptTime;

    public QuitPromptControl() {
    }

    public QuitPromptControl(String quitPrompt) {
        this.quitPrompt = quitPrompt;
    }

    /**
     *
     * @return true 提醒用户再一次退出
     *         false 进行退出处理
     */
    public boolean onBackPressed() {

        if (this.lastShowQuitPromptTime == 0L || System.currentTimeMillis() - this.lastShowQuitPromptTime > MISTIMING) {
            Androids.makeText(quitPrompt);
            this.lastShowQuitPromptTime = System.currentTimeMillis();
            return true;
        }

        return false;
    }

}

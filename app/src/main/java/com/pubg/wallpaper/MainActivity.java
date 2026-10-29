package com.pubg.wallpaper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

/**
 * Launcher screen: shows a short "Not Available" pop-up and then closes itself.
 */
public class MainActivity extends Activity {

    /** How long the pop-up stays on screen before the app closes itself. */
    private static final long POPUP_LIFETIME_MS = 2200L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Dialog popup;

    private final Runnable closeTask = new Runnable() {
        @Override
        public void run() {
            closeAndExit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNotAvailablePopup();
    }

    private void showNotAvailablePopup() {
        popup = new Dialog(this);
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup.setContentView(R.layout.dialog_not_available);
        popup.setCancelable(false);
        popup.setCanceledOnTouchOutside(false);

        Window window = popup.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0x00000000));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.dimAmount = 0.75f;
            window.setAttributes(attrs);
            window.setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.84f),
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        View card = popup.findViewById(R.id.popup_card);
        if (card != null) {
            card.startAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_in));
        }

        popup.show();
        handler.postDelayed(closeTask, POPUP_LIFETIME_MS);
    }

    private void closeAndExit() {
        handler.removeCallbacks(closeTask);
        if (popup != null && popup.isShowing()) {
            try {
                popup.dismiss();
            } catch (Exception ignored) {
            }
        }
        popup = null;
        finishAndRemoveTask();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        closeAndExit();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(closeTask);
        super.onDestroy();
    }
}

package com.pubg.wallpaper;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

/**
 * Invisible launcher screen.
 *
 * The activity window itself is fully transparent (see Theme.PubgWallpaper), so tapping the
 * launcher icon never shows an app screen. A small "Not Available" pop-up appears for a moment,
 * then the Play Store opens on a search for "pubg wallpaper" and the app closes itself.
 */
public class MainActivity extends Activity {

    /** Search term that opens in the Play Store. */
    private static final String PLAY_STORE_QUERY = "pubg wallpaper";

    /** How long the pop-up stays on screen before the store opens and the app closes. */
    private static final long POPUP_LIFETIME_MS = 1500L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Dialog popup;

    private final Runnable closeTask = new Runnable() {
        @Override
        public void run() {
            dismissPopup();
            openPlayStoreSearch();
            finishAndRemoveTask();
            overridePendingTransition(0, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No content view on purpose: the window stays invisible.
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
            // No dim behind: the screen underneath must look completely unchanged.
            window.setBackgroundDrawable(new ColorDrawable(0x00000000));
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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

    /** Opens the Play Store app search; falls back to the web store if Play Store is absent. */
    private void openPlayStoreSearch() {
        String encoded = Uri.encode(PLAY_STORE_QUERY);

        Intent market = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://search?q=" + encoded + "&c=apps"));
        market.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(market);
            return;
        } catch (ActivityNotFoundException ignored) {
            // Play Store not installed - fall through to the browser.
        }

        Intent web = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/search" + encoded + "&c=apps"));
        web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(web);
        } catch (Exception ignored) {
        }
    }

    private void dismissPopup() {
        handler.removeCallbacks(closeTask);
        if (popup != null && popup.isShowing()) {
            try {
                popup.dismiss();
            } catch (Exception ignored) {
            }
        }
        popup = null;
    }

    @Override
    public void onBackPressed() {
        dismissPopup();
        finishAndRemoveTask();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(closeTask);
        super.onDestroy();
    }
}

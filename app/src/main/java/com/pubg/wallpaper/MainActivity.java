package com.pubg.wallpaper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Invisible launcher.
 *
 * Nothing of this app is ever drawn. Tapping the icon immediately opens the PUBG Wallpaper
 * app (com.ar.pubgwallpaper) if it is installed, otherwise its Play Store listing, and then
 * this activity closes itself.
 */
public class MainActivity extends Activity {

    /** The app that should actually open. */
    private static final String TARGET_PACKAGE = "com.ar.pubgwallpaper";

    private static final String PLAY_STORE_WEB =
            "https://play.google.com/store/apps/details" + TARGET_PACKAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No content view on purpose: the window stays invisible.

        if (!openInstalledTarget()) {
            openPlayStoreListing();
        }

        finishAndRemoveTask();
        overridePendingTransition(0, 0);
    }

    /** Launches the target app if it is installed on this device. */
    private boolean openInstalledTarget() {
        try {
            Intent launch = getPackageManager().getLaunchIntentForPackage(TARGET_PACKAGE);
            if (launch == null) {
                return false;
            }
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launch);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Opens the target app's Play Store page; falls back to the web store. */
    private void openPlayStoreListing() {
        Intent market = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + TARGET_PACKAGE));
        market.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(market);
            return;
        } catch (ActivityNotFoundException ignored) {
            // Play Store not installed - fall through to the browser.
        }

        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_WEB));
        web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(web);
        } catch (Exception ignored) {
        }
    }
}

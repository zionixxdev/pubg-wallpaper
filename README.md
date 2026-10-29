# PUBG Wallpaper

Ek chhota Android app:

1. App ka apna koi screen nahi khulta (window poori transparent hai).
2. "Not Available" ka chhota pop-up aata hai.
3. ~1.5 second baad Play Store khul ke **"pubg wallpaper"** search kar deta hai.
4. App khud band ho jata hai.

## APK kaise banaye

Har `push` par GitHub Actions khud APK build karta hai.

1. Repo ke **Actions** tab mein jao.
2. **Build APK** workflow kholo.
3. Sabse latest run ke neeche **Artifacts** se `PUBG-Wallpaper-apk` download karo.
4. Phone mein install karne se pehle "Install from unknown sources" allow karna padega.

Release page par bhi seedha `PUBG-Wallpaper.apk` mil jayega.

## Local build

Android Studio mein folder kholo aur Run dabao, ya:

```bash
gradle assembleDebug
```

APK yahan milega: `app/build/outputs/apk/debug/app-debug.apk`

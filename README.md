# PUBG Wallpaper

Ek chhota Android app: app kholte hi "Not Available" ka pop-up aata hai aur
lagbhag 2 second baad app khud band ho jata hai.

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

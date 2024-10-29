# Fine

Pine and Frida better togeather.

- [Android Studio](https://developer.android.com/studio) project
- Uses `appComponentFactory` to inject Hook Loader library into the app
- Loads the hook library/script from assets folder
- Uses [Frida](https://frida.re/) Gadget and [Pine](https://github.com/canyie/pine) to hook into the app and modify its behavior
- Rootless hooking, no need for root access
- Load libraries based on arch of user system automatically
- Multi-architecture support (arm64-v8a, armeabi-v7a, x86, x86_64)

> [Blog Post](https://qbtaumai.pages.dev/posts/fine/)

If you're looking for [AndroidIDE](https://m.androidide.com/) Version of this check out [aide](https://github.com/AbhiTheModder/Fine/tree/aide) branch of this repo.

# Credits

\- [canyie](https://github.com/canyie) for [Pine](https://github.com/canyie/pine)

\- [Frida](https://frida.re/) for [Frida Gadget](https://github.com/frida/frida/releases/latest) and [Frida](https://github.com/frida/frida) itself

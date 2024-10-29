package com.abhi.pineexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.AppComponentFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;

import top.canyie.pine.Pine;
import top.canyie.pine.PineConfig;
import top.canyie.pine.callback.MethodReplacement;

public class Loader extends AppComponentFactory {

    private static boolean initialized = false;
    public static boolean isPine = false;

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    private void initialize() {
        if (!initialized) {
            try {
                Context cntxt = context.getContext();
                assert cntxt != null;
                String appDataPath = cntxt.getDataDir().getPath();
                assets.main(cntxt, "frida", new ArrayList<>());
                assets.main(cntxt, "App_dex", new ArrayList<>());
                String property = System.getProperty("os.arch");
                assert property != null;
                String archType;
                if (property.equals("arm64") || property.equals("aarch64")) {
                    archType = "arm64";
                } else if (property.equals("arm") || property.contains("armeabi")) {
                    archType = "arm";
                } else if (property.contains("x86_64")) {
                    archType = "x86_64";
                } else {
                    archType = "x86";
                }
                Log.d("Property", property);
                Log.d("archType", archType);

                switch (archType) {
                    case "arm":
                        System.load(appDataPath + "/app_libs/armeabi-v7a/libpine.so");
                        isPine = true;
                        break;
                    case "arm64":
                        System.load(appDataPath + "/app_libs/arm64-v8a/libpine.so");
                        isPine = true;
                        break;
                    case "x86_64":
                        System.load(appDataPath + "/app_libs/x86_64/libfrida-gadget.so");
                        break;
                    case "x86":
                        System.load(appDataPath + "/app_libs/x86/libfrida-gadget.so");
                        break;
                }

                Log.i("isPine: ", String.valueOf(isPine));

                if (isPine) {
                    PineConfig.debug = false;
                    PineConfig.debuggable = false;
                    try {
                        // Define a target method
                        Method isPro = Class.forName("com.abhi.pineexample.MainActivity").getDeclaredMethod("isPro");
                        // Start hook on target method
                        Pine.hook(isPro, MethodReplacement.returnConstant(true));
                    } catch (Exception ignored) {

                    }
                }
                initialized = true;
            } catch (Throwable ignored) {
            }
        }
    }

    @NonNull
    @Override
    public Activity instantiateActivityCompat(@NonNull ClassLoader cl, @NonNull String className, Intent intent) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        initialize();
        return super.instantiateActivityCompat(cl, className, intent);
    }
}

package com.abhi.pineexample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import top.canyie.pine.Pine;
import top.canyie.pine.PineConfig;
import top.canyie.pine.callback.MethodHook;
import top.canyie.pine.callback.MethodReplacement;

public class context extends Application {
    public static boolean isPine = false;
    static {
        try {
            libLoader(getContext());
        } catch (Throwable ignored) {
        }
    }

    public static Context getContext() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mBoundApplication");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(invoke);
            Field declaredField2 = obj.getClass().getDeclaredField("info");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            Method declaredMethod2 = Class.forName("android.app.ContextImpl").getDeclaredMethod("createAppContext", cls, obj2.getClass());
            declaredMethod2.setAccessible(true);
            Object invoke2 = declaredMethod2.invoke(null, invoke, obj2);
            if (invoke2 instanceof Context) {
                return (Context) invoke2;
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setIsPine(boolean isPine) {
        context.isPine = isPine;
    }

    public static boolean getIsPine() {
        return isPine;
    }

    static void libLoader(Context context) throws Exception {
        String appDataPath = context.getDataDir().getPath();
        assets.main(context, "frida", new ArrayList());
        assets.main(context, "App_dex", new ArrayList());
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
                setIsPine(true);
                break;
            case "arm64":
                System.load(appDataPath + "/app_libs/arm64-v8a/libpine.so");
                setIsPine(true);
                break;
            case "x86_64":
                System.load(appDataPath + "/app_libs/x86_64/libfrida-gadget.so");
                break;
            case "x86":
                System.load(appDataPath + "/app_libs/x86/libfrida-gadget.so");
                break;
        }

        Log.i("isPine: ", String.valueOf(isPine));

        if (getIsPine()) {
            PineConfig.debug = false;
            PineConfig.debuggable = false;
            try {
                // Define a target method
                Method isPro = Class.forName("com.newpine.example.MainActivity").getDeclaredMethod("isPro");
                // Start hook on target method
                Pine.hook(isPro, MethodReplacement.returnConstant(true));
            } catch (Exception ignored) {

            }
        }
    }

}

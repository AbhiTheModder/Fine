package com.abhi.pineexample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class context extends Application {

    public static Context getContext() {
        try {
            @SuppressLint("PrivateApi") Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null, new Object[0]);
            @SuppressLint("DiscouragedPrivateApi") Field declaredField = cls.getDeclaredField("mBoundApplication");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(invoke);
            assert obj != null;
            Field declaredField2 = obj.getClass().getDeclaredField("info");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            assert obj2 != null;
            @SuppressLint("PrivateApi") Method declaredMethod2 = Class.forName("android.app.ContextImpl").getDeclaredMethod("createAppContext", cls, obj2.getClass());
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
}

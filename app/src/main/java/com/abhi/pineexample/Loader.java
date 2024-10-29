package com.abhi.pineexample;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.AppComponentFactory;

public class Loader extends AppComponentFactory {

    private static boolean initialized = false;

    private void initialize() {
        if (!initialized) {
            try {
                new context();
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

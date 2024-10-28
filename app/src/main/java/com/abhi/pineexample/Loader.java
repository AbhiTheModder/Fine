package com.abhi.pineexample;

import android.app.AppComponentFactory;


public class Loader extends AppComponentFactory {
    static {
        try {
            context.libLoader(context.getContext());
        } catch (Throwable ignored) {
        }
    }
}

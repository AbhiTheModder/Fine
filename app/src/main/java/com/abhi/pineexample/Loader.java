package com.abhi.pineexample;

import android.app.AppComponentFactory;


public class Loader extends AppComponentFactory {
    static {
        try {
            new context();
        } catch (Throwable ignored) {
        }
    }
}

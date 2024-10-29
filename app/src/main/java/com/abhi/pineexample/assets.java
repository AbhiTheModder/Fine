package com.abhi.pineexample;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

public class assets {
    public static boolean assets(AssetManager assetManager, String str, String str2) {
        if (assetManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                File file = new File(str2);
                file.delete();
                Objects.requireNonNull(file.getParentFile()).mkdirs();
                bufferedInputStream = new BufferedInputStream(assetManager.open(str));
                fileOutputStream = new FileOutputStream(str2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                try {
                    bufferedInputStream.close();
                } catch (Exception ignored) {
                }
                try {
                    fileOutputStream.close();
                } catch (Exception ignored) {
                }
                return true;
            } catch (Exception e3) {
                e3.printStackTrace();
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                return false;
            }
        } finally {

        }
    }
    public static void main(Context context, String str, List<String> list) throws Exception {
        AssetManager assets = context.getAssets();
        String[] list2 = context.getAssets().list(str);
        if (list2 == null) {
            return;
        }
        for (String str2 : list2) {
            if (str2.contains(".")) {
                File file = new File(context.getDir("libs", 0), convert(str));
                if (!file.exists()) {
                    file.mkdirs();
                }
                String str3 = file.getAbsolutePath() + "/" + str2;
                if (assets(assets, str + "/" + str2, str3)) {
                    list.add(str3);
                }
            } else {
                main(context, str + "/" + str2, list);
            }
        }
    }
    public static String convert(String str) {
        String[] strings = str.replaceAll("\\\\", "/").split("/");
        return strings[strings.length - 1];
    }
}

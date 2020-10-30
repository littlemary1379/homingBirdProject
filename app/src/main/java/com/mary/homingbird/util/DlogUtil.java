package com.mary.homingbird.util;

import android.util.Log;

public class DlogUtil {
    public static boolean debugMode = true;

    public static final void d(String TAG, Object object) {
        if (debugMode) {
            Log.d(TAG, buildMessage(object));
        }
    }

    private static String buildMessage(Object object) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("DlogUtil");
        sb.append(" :: ");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append(" :: ");
        sb.append((String)object);

        return sb.toString();
    }
}

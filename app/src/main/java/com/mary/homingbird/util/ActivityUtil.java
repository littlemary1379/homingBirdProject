package com.mary.homingbird.util;

import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

    public static void startActivityWithoutFinish(Context context, Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}

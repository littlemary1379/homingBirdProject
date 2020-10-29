package com.mary.homingbird.util;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import com.mary.homingbird.R;

public class ProgressBarUtil {

    private int i;
    private ProgressBar progressBar;
    private Handler handler = new Handler(Looper.myLooper());

    public ProgressBarUtil(ProgressBar progressBar){
        this.progressBar = progressBar;
    }

    public void setProgressBar(){
        int color = progressBar.getResources().getColor(R.color.color_eb9f9f);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        i=progressBar.getProgress();
        new Thread(() -> {
            while (i < 100) {
                i += 1;

                handler.post(() -> progressBar.setProgress(i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

package com.dev.soenkek.redditviewer.transformer;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class PostStackTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
//        FIXME swipe right, not scaled sometimes
        if (position >= 0) {
            page.setScaleX(0.8f-position/50);
            page.setScaleY(0.8f-position/50);
            page.setTranslationX(-page.getWidth()*position+15*position);
            page.setTranslationY(20*position);
            page.setElevation(16-position*4);
        }
    }
}

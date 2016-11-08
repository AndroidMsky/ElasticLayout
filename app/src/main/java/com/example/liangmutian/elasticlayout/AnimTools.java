package com.example.liangmutian.elasticlayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wuduogen838 on 16/11/8.
 */

public class AnimTools {
    public static void anim(final View view, int startHeight, int endHeight, int duration){
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "view", startHeight,endHeight ).setDuration(duration);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int cVal = (int) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.height = cVal;
                view.setLayoutParams(lp);
            }
        });
    }


    public static int dip(int value, Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }



}

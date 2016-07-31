package com.gamik.pastify.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gamik.pastify.R;

/**
 * Decorator class
 */
public class SmartDecorator extends RecyclerView.ItemDecoration {

    private Context context;
    private float startX;
    private boolean endMargin;

    /**
     * Constructor for Decorator class
     */
    public SmartDecorator(Context context) {
        this.context = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1.0f);
        View view1 = parent.getChildAt(0);
        View view2 = view1.findViewById(R.id.rl_options);
        //View view3 = view2.findViewById(R.id.user_account_value);
        for (int i = 0, count = parent.getChildCount(); i < count; ++i) {
            View child = parent.getChildAt(i);
            float startY = child.getBottom();
            float startX = view2.getLeft() + 30;
            float stopX = child.getRight();
            c.drawLine(startX, startY, stopX, startY, paint);
        }

    }
}

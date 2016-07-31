package com.gamik.pastify.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Decorator class
 */
public class Decorator extends RecyclerView.ItemDecoration {

    private Context context;
    private float startX;
    private boolean endMargin;

    /**
     * Constructor for Decorator class
     */
    public Decorator(Context context, float startX, boolean endMargin) {
        this.context = context;
        this.startX = startX;
        this.endMargin = endMargin;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1.0f);
        float startX = this.startX;
        for (int i = 0, count = parent.getChildCount(); i < count; ++i) {
            View child = parent.getChildAt(i);
            float startY = child.getBottom();
            float stopX = 0;
            if (endMargin) {
                stopX = child.getRight() - 20.0f;
            } else {
                stopX = child.getRight();
            }
            c.drawLine(startX, startY, stopX, startY, paint);
        }

    }
}

package com.papb2.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DialView extends View {
    private static int SELECTION_COUNT = 4;
    private float theWidth;
    private float theHeight;
    private float theRadius;
    private Paint textPaint;
    private Paint dialPaint;

    private int activeSelection;
    private final StringBuffer tempLabel = new StringBuffer(8);
    private final float[] tempResult = new float[2];

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40f);

        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint.setColor(Color.GRAY);

        activeSelection = 0;

        setOnClickListener(view -> {
            activeSelection = (activeSelection + 1) % SELECTION_COUNT;

            if (activeSelection >= 1) {
                dialPaint.setColor(Color.DKGRAY);
            } else {
                dialPaint.setColor(Color.GRAY);
            }

            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        theWidth = w;
        theHeight = h;
        theRadius = (float) (Math.min(theWidth, theHeight / 2 * 0.8));
    }

    private float[] computeXYForLocation(final int pos, final float radius) {
        float[] result = tempResult;
        double startAngle = Math.PI * (9 / 8d);
        double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (theWidth / 2);
        result[1] = (float) (radius * Math.sin(angle)) + (theHeight / 2);

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(theWidth / 2, theHeight / 2, theRadius, dialPaint);

        // draw text
        final float labelRadius = theRadius + 20;
        StringBuffer label = tempLabel;

        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYForLocation(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];

            label.setLength(0);
            label.append(i);

            canvas.drawText(label, 0, label.length(), x, y, textPaint);
        }

        // draw circle mark
        final float markerRadius = theRadius - 35;
        float[] xyData = computeXYForLocation(activeSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];

        @SuppressLint("DrawAllocation") Paint newPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        newPaint.setColor(Color.YELLOW);
        canvas.drawCircle(x, y, 25, newPaint);
    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}

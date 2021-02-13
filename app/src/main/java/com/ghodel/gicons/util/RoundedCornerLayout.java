package com.ghodel.gicons.util;

import android.widget.FrameLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class RoundedCornerLayout extends FrameLayout {
    private double mCornerRadius;

    public RoundedCornerLayout(Context context) {
        this(context, null, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public double getCornerRadius() {
        return mCornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        mCornerRadius = cornerRadius;
    }

    @Override
    public void draw(Canvas canvas) {
        int count = canvas.save();

        final Path path = new Path();
        path.addRoundRect(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), (float) mCornerRadius, (float) mCornerRadius, Path.Direction.CW);
        canvas.clipPath(path, Region.Op.REPLACE);

        canvas.clipPath(path);
        super.draw(canvas);
        canvas.restoreToCount(count);
    }
}

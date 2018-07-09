package yangxinlang.bawei.com.yangxinlang180709;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    private int width;
    private int height;
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Bitmap mbitmap;
    private Canvas mcanvas;
    private float dowmx, dowmy;
    private float tempx, tempy;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
        initPint();
    }

    private void initBitmap() {
        //设置底层图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.u4545);
    }
//设置画笔
    private void initPint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(25);
        paint.setTextSize(25);
        paint.setDither(true);
        path = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        //设置上层图片
        mbitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas(mbitmap);
        //设置上层颜色
        mcanvas.drawColor(Color.BLUE);
    }
     //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, null);
        drawPath();
        canvas.drawBitmap(mbitmap, 0, 0, null);

    }


    private void drawPath() {
        //处理重叠痕迹
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mcanvas.drawPath(path, paint);

    }
    //设置分发时间
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dowmx = event.getX();
                dowmy = event.getY();
                path.moveTo(dowmx, dowmy);
                invalidate();
                tempx = dowmx;
                tempy = dowmy;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                path.quadTo(tempx, tempy, x, y);
                invalidate();
                tempx = x;
                tempy = y;
                break;
        }


        return true;
    }
}



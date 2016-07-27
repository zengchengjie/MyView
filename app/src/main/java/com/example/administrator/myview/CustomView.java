package com.example.administrator.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/27.
 */
public class CustomView extends View {
    private float mborder_width;
    private int mboder_color;

    /**
     * 初始化画笔
     */
    private Paint mPaint;
    /**
     * 定义一块矩形区域
     */
    private RectF mBounds;
    /**
     * 定义矩形的宽和长
     */
    private float width;
    private float height;
    /**
     * 绘制的圆的半径
     */
    float radius;
    /**
     * 定义矩形的最小和最大长度
     */
    float smallLength;
    float largeLength;

    /**
     * 构造方法1 有一个参数
     *
     * @param context 上下文
     */
    public CustomView(Context context) {
        super(context);
        init();//初始化画笔
    }

    /**
     * 构造方法2 有两个参数
     *
     * @param context 上下文
     * @param attrs   返回属性的数量？
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();//初始化画笔

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);

        mboder_color = typedArray.getColor(R.styleable.CustomView_border_color, 0xff000000);
        mborder_width = typedArray.getDimension(R.styleable.CustomView_border_width, 2);

        typedArray.recycle();
    }

    /**
     * 有三个参数的构造函数
     *
     * @param context      上下文
     * @param attrs        返回属性的数量
     * @param defStyleAttr ？
     */
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();//初始化画笔
    }

/*public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/
    //第四个构造函数暂时不能使用 它支持API 21+


    /*
    初始化画笔
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mborder_width);
        mPaint.setColor(mboder_color);
    }


    //这个方法是用来绘制View这个Canvas用的重要方法，所有canvas绘图的方法都适用于此
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xff000000);
        mPaint.setColor(0x66555555);
        /**
         * 画一个矩形
         * 参数是RectF rectf(构造方法传参---->左上右下的坐标 ), float rx, float ry ， Paint paint
         */
        canvas.drawRoundRect(new RectF(mBounds.centerX() - (float) 0.9 * width / 2
                , mBounds.centerY() - (float) 0.9 * height / 2
                , mBounds.centerX() + (float) 0.9 * width / 2
                , mBounds.centerY() + (float) 0.9 * height / 2), 30, 30, mPaint);//椭圆的X,Y,画笔

        mPaint.setColor(mboder_color);//设置画笔颜色
/**
 * 画一个圆
 */
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), radius, mPaint);//圆心X,Y,半径，画笔

        float start_x, start_y;
        float end_x, end_y;
        for (int i = 0; i < 60; ++i) {
            start_x = radius * (float) Math.cos(Math.PI / 180 * i * 6);
            start_y = radius * (float) Math.sin(Math.PI / 180 * i * 6);
            if (i % 5 == 0) {
                end_x = start_x + largeLength * (float) Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y + largeLength * (float) Math.sin(Math.PI / 180 * i * 6);
            } else {
                end_x = start_x + smallLength * (float) Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y + smallLength * (float) Math.sin(Math.PI / 180 * i * 6);
            }
            start_x += mBounds.centerX();
            end_x += mBounds.centerX();
            start_y += mBounds.centerY();
            end_y += mBounds.centerY();
            canvas.drawLine(start_x, start_y, end_x, end_y, mPaint);
        }
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), 20, mPaint);
        canvas.rotate(60, mBounds.centerX(), mBounds.centerY());
        canvas.drawLine(mBounds.centerX(), mBounds.centerY(), mBounds.centerX(), mBounds.centerY() - radius, mPaint);
    }

/*这个方法在界面的尺寸更改的时候会被调用，一般是在屏幕旋转的时候会被调用，有两个新w/h和旧w/h会被传入，
这里我用来实现初始化绘图的时候边框（RecF类型，具体见Canvas绘图中的Oval和矩形绘制类）等。*/

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化全局参数，主要是在onsizeChanged（）中 这是为了自适应界面，
        // 每次界面变化的时候都会被调用来重新计算一些参数
        mBounds = new RectF(getLeft(), getTop(), getRight(), getBottom());

        width = mBounds.right - mBounds.left;
        height = mBounds.bottom - mBounds.top;

        if (width < height) {
            radius = width / 4;
        } else {
            radius = height / 4;
        }

        smallLength = 10;
        largeLength = 20;
    }
}

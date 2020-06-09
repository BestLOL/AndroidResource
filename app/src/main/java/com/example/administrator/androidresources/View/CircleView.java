package com.example.administrator.androidresources.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidresources.R;

//继承于View,wrap_content和padding需要重写
public class CircleView extends View {

    private int myColor = Color.RED;
    private Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        this(context,null,0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);
        myColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);
        a.recycle();
        init();
    }

    //初始化
    private void init(){
        myPaint.setColor(myColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth = 200;
        int mHeight = 200;

        if(getLayoutParams().width==ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(200,200);
        }else if(getLayoutParams().width==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(200,heightSize);
        }else if(getLayoutParams().height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(wideSize,200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth()-paddingLeft-paddingRight;
        int height = getHeight()-paddingTop-paddingBottom;
        int radius = Math.min(width,height)/2;
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,radius,myPaint);
    }
}

package com.hpe.yanliu.surfaceviewpro;

import android.app.Notification;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by yanliu on 2016/2/14.
 */
//使用SurfaceView的时候需要添加回调函数,引用SurfaceHolder包下的
    //因为是自定义控件，所以需要添加的是两个参数的构造方法
public class MyView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{
    //创建画笔对象和路径
    private Paint p = new Paint();
    private Path path = new Path();

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //使用SurfaceView添加回调函数
        getHolder().addCallback(this);
        //画笔初始化，设置画笔的颜色
        p.setColor(Color.RED);
        //初始化画笔的大小
        p.setTextSize(10);
        //给画笔清理锯齿
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        //添加监听
        setOnTouchListener(this);
    }
    //创建绘制方法
    public void draw(){
        //要绘制的话肯定要有一个画布,要通过getHolder()锁定画布,
        Canvas canvas = getHolder().lockCanvas();
        //初始化画布的颜色
        canvas.drawColor(Color.WHITE);
        //用drawPath进行绘制
        canvas.drawPath(path,p);
        //绘制结束后要解锁画布
        getHolder().unlockCanvasAndPost(canvas);
    }

    //这个方法用来清理画布
    public void clear(){
        //清除路径
        path.reset();
        //路径重置后调用draw方法
        draw();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //在surface被改变的时候调用方法的执行
        draw();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            //处理按下事件
            case MotionEvent.ACTION_DOWN:
                //按下的时候通过moveTo()绘制按下的这个点,获取按下点的X和Y坐标
                path.moveTo(event.getX(), event.getY());
                //获取之后调用draw()方法进行绘制
                draw();
                break;

            //在移动的时候进行绘制
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                draw();
                break;
        }
        return true;
    }
}

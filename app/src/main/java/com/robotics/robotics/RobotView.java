package com.robotics.robotics;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by xavier on 04/02/15.
 */
public class RobotView extends SurfaceView implements View.OnClickListener,
        View.OnTouchListener, SurfaceHolder.Callback, Runnable {

    Activity parentActivity;

    private Resources robotRes;
    private Context robotcontext;

    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;

    public RobotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();// Recuperation du Holder
        holder.addCallback(this);// Ajout

        robotcontext = context;
        robotRes = robotcontext.getResources();

        cv_thread = new Thread(this);
        setFocusable(true);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void run() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

package com.robotics.robotics;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
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

    //image des commandes
    private Bitmap connectionON;
    private Bitmap connectionOF;
    private Bitmap ledON;
    private Bitmap ledOF;
    private Bitmap fleche_Avance;
    private Bitmap fleche_Recule;
    private Bitmap fleche_Droite;
    private Bitmap fleche_Gauche;

    int flecheSizeW = 0;
    int flecheSizeH = 0;

    int connectionSizeW = 0;
    int connectionSizeH = 0;

    int ledSizeW = 0;
    int ledSizeH = 0;


    public RobotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();// Recuperation du Holder
        holder.addCallback(this);// Ajout

        robotcontext = context;
        robotRes = robotcontext.getResources();
        loadimages(robotRes);

        cv_thread = new Thread(this);
        setFocusable(true);
        setOnClickListener(this);
    }

    private void loadimages(Resources res) {
        Log.i("-> Fct <-", " loadimages ");

        // Chargement Img eta connection
        connectionON = BitmapFactory.decodeResource(res, R.drawable.img_connection_on);
        connectionOF = BitmapFactory.decodeResource(res,R.drawable.img_connection_of);

        //Chagement Img eta led
        ledON = BitmapFactory.decodeResource(res,R.drawable.img_led_on);
        ledOF = BitmapFactory.decodeResource(res,R.drawable.img_led_of);

        // Chargement Img Fleche
        fleche_Avance = BitmapFactory.decodeResource(res, R.drawable.img_fleche_avance);
        fleche_Recule = BitmapFactory.decodeResource(res,R.drawable.img_fleche_recule);
        fleche_Droite = BitmapFactory.decodeResource(res,R.drawable.img_fleche_droite);
        fleche_Gauche = BitmapFactory.decodeResource(res,R.drawable.img_fleche_gauche);

        // Definition de la taille de chaque images
        flecheSizeW = fleche_Avance.getWidth();
        flecheSizeH = fleche_Avance.getHeight();

        connectionSizeW = connectionON.getWidth();
        connectionSizeH = connectionON.getHeight();

        ledSizeW = ledON.getWidth();
        ledSizeH = ledON.getHeight();

        Log.i(">>> Projet", " loadimage ");

    }

    public void initparameters() {
        Log.i("-> Fct <-", " initparameters ");

        // Limite haute matrice
        double temp = getHeight() / 2.2;
       /* mapTopAnchor = (int) temp;

        // Limite gauche matrice
        mapLeftAnchor = (getWidth() - mapWidth * mapTileSize) / 2;

        // Limite basse matrice
        mapBotAnchor = getHeight() / 2 + mapWidth * mapTileSize;

        // Limite droite matrice
        mapRightAnchor = getWidth() - mapLeftAnchor;

        // limite haute petite matrice
        mapTopAnchorMinus = (getHeight() - mapHeight * mapTileSizeMinus - case_Bleu
                .getHeight()) / 6;

        // Limite gauche petite matrice
        mapLeftAnchorMinus = (getWidth() - mapWidth * mapTileSizeMinus) / 2;*/



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
        Log.i("-> Fct <-", " run ");
        Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(40);
                try {

                    c = holder.lockCanvas(null);
                    dessin(c);

                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
                cv_thread.currentThread().interrupt();
                break;
            }
        }
        cv_thread.interrupt();
    }

    public void paintCommande(Canvas canvas) {
        // Log.i("-> Fct <-", " paintCommande ");

        canvas.drawBitmap(fleche_Avance, flecheSizeW, flecheSizeH, null);


                /*canvas.drawBitmap(tmpSmallimg, mapLeftAnchorMinus + j
                        * mapTileSizeMinus, mapTopAnchorMinus + i
                        * mapTileSizeMinus, null);*/


    }


    private void dessin(Canvas canvas) {
        // Log.i("-> Fct <-", " dessin ");
        canvas.drawRGB(105, 105, 105);
        paintCommande(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initparameters();
        in=true;
        cv_thread = new Thread(this);
        cv_thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    public void setThread(boolean etat){
        this.in=etat;

    }

}

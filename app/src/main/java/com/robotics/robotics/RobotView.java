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

    //Definition du decalage a porté aux images
    int decaFlecheH = 2;
    int decaFlecheW = 20;

    float positionClickX;
    float positionClickY;


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


        //Log.i(">>> Projet", " loadimage ");

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
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // Log.d("onTouchEvent", "r�cup�ration de la position du doigt");
        positionClickX = event.getX();// recuperation position X
        positionClickY = event.getY();// recuperation position Y

        Log.i("x"+positionClickX, " y "+positionClickY);
        switch (event.getAction()) {// Swtich sur le type d'action
            case MotionEvent.ACTION_MOVE:
                // System.out.println("ACTION_MOVE");



                break;
            case MotionEvent.ACTION_DOWN:
                //System.out.println("ACTION_DOWN");
                if(((positionClickX>(flecheSizeW*decaFlecheW)) && (positionClickX<((flecheSizeW*decaFlecheW)+flecheSizeW))) && ((positionClickY>(flecheSizeH*decaFlecheH)) &&(positionClickY<((flecheSizeH*decaFlecheH)+flecheSizeH)))){
                    Log.i("-> Fct onTouch <-", " Avance ");
                }
                else if(((positionClickX>(flecheSizeW*(decaFlecheW-1))) && (positionClickX<((flecheSizeW*decaFlecheW)))) && ((positionClickY>(flecheSizeH*(decaFlecheH+1))) &&(positionClickY<((flecheSizeH*(decaFlecheH+1))+flecheSizeH)))){
                    Log.i("-> Fct onTouch <-", " Gauche ");
                }else if(((positionClickX>(flecheSizeW*(decaFlecheW+1))) && (positionClickX<((flecheSizeW*(decaFlecheW+1))+flecheSizeW))) && ((positionClickY>(flecheSizeH*(decaFlecheH+1))) &&(positionClickY<((flecheSizeH*(decaFlecheH+1))+flecheSizeH)))){
                    Log.i("-> Fct onTouch <-", " Droite ");
                }else if(((positionClickX>(flecheSizeW*decaFlecheW)) && (positionClickX<((flecheSizeW*decaFlecheW)+flecheSizeW))) && ((positionClickY>(flecheSizeH*(decaFlecheH+2))) &&(positionClickY<((flecheSizeH*(decaFlecheH+2))+flecheSizeH)))){
                    Log.i("-> Fct onTouch <-", " Arriere ");
                }else if(((positionClickX>connectionSizeW) && (positionClickX<(connectionSizeW*2))) && ((positionClickY>connectionSizeH) &&(positionClickY<(connectionSizeH*2)))){
                    Log.i("-> Fct onTouch <-", " Bluetooth ");
                }else if(((positionClickX>(ledSizeW*3)) && (positionClickX<((ledSizeW*4)))) && ((positionClickY>ledSizeH) &&(positionClickY<(ledSizeH*2)))){
                    Log.i("-> Fct onTouch <-", " Led ");
                }else{
                    Log.i("-> Fct onTouch <-", " rien ");
                }

                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("ACTION_UP");

                break;
            default:
                System.out.println();
        }

        return super.onTouchEvent(event);
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

        //dessinage des fleches
        canvas.drawBitmap(fleche_Avance, flecheSizeW*decaFlecheW, flecheSizeH*decaFlecheH, null);
        canvas.drawBitmap(fleche_Gauche, flecheSizeW*(decaFlecheW-1), flecheSizeH*(decaFlecheH+1), null);
        canvas.drawBitmap(fleche_Droite, flecheSizeW*(decaFlecheW+1), flecheSizeH*(decaFlecheH+1), null);
        canvas.drawBitmap(fleche_Recule, flecheSizeW*decaFlecheW, flecheSizeH*(decaFlecheH+2), null);

         //dessinage de la conection bluethoot
        canvas.drawBitmap(connectionOF, connectionSizeW, connectionSizeH, null);
        //canvas.drawBitmap(connectionON, connectionSizeW, connectionSizeH, null);

        //dessinage de l'allumage des led
        canvas.drawBitmap(ledOF, ledSizeW*3, ledSizeH, null);
        //canvas.drawBitmap(ledON, ledSizeW*2, ledSizeH, null);


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

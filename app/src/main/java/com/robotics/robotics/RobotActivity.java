package com.robotics.robotics;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class RobotActivity extends ActionBarActivity {

    RobotView robot;
    //BtInterface bl;
    private TextView logview;
    private long lastTime = 0;


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String data = msg.getData().getString("receivedData");

            long t = System.currentTimeMillis();
            if(t-lastTime > 100) {// Pour éviter que les messages soit coupés
                logview.append("\n");
                lastTime = System.currentTimeMillis();
            }
            logview.append(data);
        }
    };

    final Handler handlerStatus = new Handler() {
        public void handleMessage(Message msg) {
            //int co = msg.arg1;
            //if(co == 1) {
                logview.append("Connected\n");
            //} else if(co == 2) {
            //    logview.append("Disconnected\n");
           // }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_robot);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        robot=(RobotView)findViewById(R.id.RobotView);
        robot.parentActivity=this;
        robot.setVisibility(View.VISIBLE);

        //bl = new BtInterface(handlerStatus, handler);
        robot.bl = new BtInterface(handlerStatus, handler);
        logview = (TextView)findViewById(R.id.logview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_robot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("-> FCT <-", "onPause");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("-> FCT <-", "onResume");
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("-> FCT <-", "onStop");
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        return super.onKeyDown(keyCode, event);
    }
}

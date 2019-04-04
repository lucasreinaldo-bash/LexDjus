package vostore.lexdjus.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.net.URL;

import vostore.lexdjus.R;


/**
 * Created by Yaakov Shahak on 14/12/2016.
 */

public class LockScreenService extends Service implements View.OnClickListener {


    private RelativeLayout linearLayout;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager windowManager;


    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();



        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenReceiver, intentFilter);
        windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN //draw on status bar
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION,// hiding the home screen button
                PixelFormat.TRANSLUCENT);
    }

    private void init() {
        linearLayout = new RelativeLayout(this);
        windowManager.addView(linearLayout, layoutParams);
        ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_main, linearLayout);
        View btnClose = linearLayout.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        windowManager.removeView(linearLayout);
        linearLayout = null;
    }



    BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) && linearLayout == null) {
                init();
            }
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        IntentFilter filterScreen = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filterScreen.addAction(Intent.ACTION_SCREEN_OFF);
        filterScreen.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filterScreen.addAction(Intent.ACTION_TIME_CHANGED);


        registerReceiver(screenReceiver, filterScreen);

        return START_STICKY;
    }

}
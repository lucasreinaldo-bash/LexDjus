package vostore.lexdjus.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Service.START_STICKY;

public class UpdateService extends Service {
    BroadcastReceiver mReceiver;
    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    // Intent myIntent;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate() {

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Artigos/nome");
   // mDatabase = FirebaseDatabase.getInstance().getReference();

    String teste = mDatabase.toString();




     /*try{
     StateListener phoneStateListener = new StateListener();
     TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
     telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
     }catch(Exception e){
    	 System.out.println(e);
     }*/

    /* myIntent = new Intent(MyService.this,LockScreenAppActivity.class);
     myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     Bundle myKillerBundle = new Bundle();
     myKillerBundle.putInt("kill",1);
     myIntent.putExtras(myKillerBundle);*/

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        Toast.makeText(this, teste, Toast.LENGTH_SHORT).show();

        mReceiver = new BroadcastLeis();
        registerReceiver(mReceiver, filter);


        super.onCreate();


    }
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub

        super.onStart(intent, startId);
    }

/*class StateListener extends PhoneStateListener{
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        super.onCallStateChanged(state, incomingNumber);
        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                System.out.println("call Activity off hook");
            	getApplication().startActivity(myIntent);



                break;
            case TelephonyManager.CALL_STATE_IDLE:
                break;
        }
    }
};*/


    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}

package vostore.lexdjus.Services;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import vostore.lexdjus.MainActivity;

public class BroadcastLeis extends BroadcastReceiver {
    public BroadcastLeis() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
           boolean telaStatus = intent.getBooleanExtra(
                   Intent.ACTION_SCREEN_ON, true
           );
           if(telaStatus){
               context.startService(new Intent(context, LockScreenService.class));
               Toast.makeText(context, "Hehe", Toast.LENGTH_SHORT).show();
           }else{
               context.startService(new Intent(context, LockScreenService.class));
           }
        }

    }

}


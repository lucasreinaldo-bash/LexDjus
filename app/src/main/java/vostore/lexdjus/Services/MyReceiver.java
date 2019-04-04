package vostore.lexdjus.Services;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;

public class MyReceiver implements GlobalReceiverCallBack  {
    public MyReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageInstaller.Session.getGlobalReceiverCallBack(context, intent);

        //Log.e("dfd", "" + intent.getAction());
    }

}

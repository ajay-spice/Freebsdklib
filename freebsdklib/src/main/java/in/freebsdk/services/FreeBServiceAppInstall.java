package in.freebsdk.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FreeBServiceAppInstall extends Service{
    /** A client is binding to the service with bindService() */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
    /** The service is starting, due to a call to startService() */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return super.onStartCommand(intent, flags, startId);
	}
	

}

package alvarado.wuil;

import alvarado.wuil.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class SamplePushActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GCMRegistrar.checkDevice(this);
		//uncomment below lines to unregister the device 
//		GCMRegistrar.unregister(this);
//		Log.d("info",
//				"unregistereddd....." + GCMRegistrar.getRegistrationId(this));
		GCMRegistrar.checkManifest(this);
		if (GCMRegistrar.isRegistered(this)) {
			Log.d("info", GCMRegistrar.getRegistrationId(this));
		}
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// replace this with the project ID
			GCMRegistrar.register(this, "197161168597");
			//GCMRegistrar.register(this, "576574596410");
			Log.d("info", GCMRegistrar.getRegistrationId(this));
		} else {
			Log.d("info", "already registered as" + regId);
		}
	}
}
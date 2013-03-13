package com.researchmobile.coretel.view;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import android.app.Application;
@ReportsCrashes(formKey = "dFNZNU9yaGQ5MjdGVFM5SFpkeW12Snc6MQ",  logcatArguments = {"-t", "100", "-v", "long", "ActivityManager: I", "MyApp: D", "*: S"})

public class AcraClass extends Application {
@Override
public void onCreate() {
super.onCreate();
// The following line triggers the initialization of ACRA
ACRA.init(this);
}
}
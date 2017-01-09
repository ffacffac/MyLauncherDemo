package com.mylauncherdemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by huangqj on 2016/11/8.
 */
public class DemoApplication extends Application
{

	//    private RefWatcher refWatcher;
	//
	//    public static RefWatcher getRefWatcher(Context context) {
	//        DemoApplication application = (DemoApplication) context
	//                .getApplicationContext();
	//        return application.refWatcher;
	//    }

	@Override
	public void onCreate()
	{
		super.onCreate();
		//初始化LeakCanary
		if (LeakCanary.isInAnalyzerProcess(this))
		{
			return;
		}
		LeakCanary.install(this);
		//        enabledStrictMode();
	}

	//    private void enabledStrictMode() {
	//        if (SDK_INT >= GINGERBREAD) {
	//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
	//                    .detectAll() //
	//                    .penaltyLog() //
	//                    .penaltyDeath() //
	//                    .build());
	//        }
	//    }
}

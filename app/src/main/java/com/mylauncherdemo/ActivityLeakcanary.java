package com.mylauncherdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

public class ActivityLeakcanary extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content_activity_leakcanary);
		//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//        setSupportActionBar(toolbar);

		//        //在自己的应用初始Activity中加入如下两行代码
		//        RefWatcher refWatcher = DemoApplication.getRefWatcher(this);
		//        refWatcher.watch(this);

		//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		//        fab.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View view) {
		//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
		//                        .setAction("Action", null).show();
		//            }
		//        });
		setNavigationBarVisibility(true);
		//        initView();
		//
		//        init();
		//
		//        mHandler.post(mRun);
	}

	private void initView()
	{
		Button btnExecute = (Button) findViewById(R.id.btn_execute);
		btnExecute.setVisibility(View.GONE);
		btnExecute.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startAsyncTask();
			}
		});
	}

	void startAsyncTask()
	{
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected Void doInBackground(Void... params)
			{
				SystemClock.sleep(15000);
				return null;
			}
		}.execute();
	}

	private void setNavigationBarVisibility(boolean visible)
	{
		//        int flag = 0;
		//        if (!visible) {
		//            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		//        }
		//        getWindow().getDecorView().setSystemUiVisibility(flag);

		View decorView = getWindow().getDecorView();
		// Hide the status bar.
//		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//				int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
//				int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//				int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//				int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//				int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//				int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE;
				int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
//		android.app.ActionBar actionBar = getActionBar();
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();


	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//在自己的应用初始Activity中加入如下两行代码
		//        RefWatcher refWatcher = DemoApplication.getRefWatcher(getApplicationContext());
		//        refWatcher.watch(getApplicationContext());
		//        getLocalBroadcastManager().unregisterReceiver(mExitReceiver);
		//        mHandler.removeCallbacks(mRun);
		//        mHandler.removeCallbacksAndMessages(null);
	}


	public final static String ACTION_EXIT_APP = "cn.edu.zafu.leakcanary.exit";
	private LocalBroadcastManager mLocalBroadcatManager;
	private BroadcastReceiver mExitReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (action.equals(ACTION_EXIT_APP))
			{
				Log.d("TAG", "exit from broadcast");
				finish();
			}
		}
	};

	private void init()
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_EXIT_APP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		getLocalBroadcastManager().registerReceiver(mExitReceiver, filter);
	}

	private LocalBroadcastManager getLocalBroadcastManager()
	{
		if (mLocalBroadcatManager == null)
		{
			mLocalBroadcatManager = LocalBroadcastManager.getInstance(this);
		}
		return mLocalBroadcatManager;
	}

	/**
	 *
	 */
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Toast.makeText(ActivityLeakcanary.this, "两秒执行一次", Toast.LENGTH_SHORT).show();
		}
	};

	Runnable mRun = new Runnable()
	{
		@Override
		public void run()
		{
			mHandler.postDelayed(mRun, 2000);
			mHandler.sendEmptyMessage(1);
		}
	};
}

package com.mylauncherdemo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, AdapterView.OnItemLongClickListener
{

	private GridView       gvApp;
	private PackageManager mPm;
	private List<ResolveInfo> mAllApps = new ArrayList<ResolveInfo>();
	private GVAppAdapter gvAppAdapter;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		initView();

		//        onUserLeaveHint();
	}

	private void initView()
	{
		mPm = getPackageManager();
		gvApp = (GridView) findViewById(R.id.gv_allapps_icon);
		bindAllApps();
		gvAppAdapter = new GVAppAdapter(this, mAllApps, mPm);
		gvApp.setAdapter(gvAppAdapter);
		gvApp.setOnItemClickListener(this);
		gvApp.setOnItemLongClickListener(this);

		//        @SuppressLint("ServiceCast") StatusBarManager mStatusBarManager = (StatusBarManager) getSystemService("statusbar");
		//        mStatusBarManager.disable(StatusBarManager.DISABLE_EXPAND);//禁止使用状态栏
		//        mStatusBarManager.disable(StatusBarManager.DISABLE_NONE);//不禁止
	}

	private void bindAllApps()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// mAllApps = mPm.queryIntentActivities(intent, 0);
		List<ResolveInfo> resinfos = mPm.queryIntentActivities(intent, 0);
		if (resinfos != null && resinfos.size() > 0)
		{
			int i = 0;
			for (ResolveInfo resolveInfo : resinfos)
			{
				String pgk = resolveInfo.activityInfo.packageName;
				String cls = resolveInfo.activityInfo.name;
				if (i < 4 || pgk.equals("com.homewin") || pgk.equals("hw.ipsp.jcw.app"))
				{
					i++;
					mAllApps.add(resolveInfo);
				}
			}
		}
		Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPm));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		switch (parent.getId())
		{
			case R.id.gv_allapps_icon:
				// ?????????????
				ResolveInfo res = mAllApps.get(position);
				String pkg = res.activityInfo.packageName;// ????????
				String cls = res.activityInfo.name;// ???????????
				ComponentName component = new ComponentName(pkg, cls);
				Intent intent = new Intent();
				intent.setComponent(component);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	@Override
	public void onBackPressed()
	{
		 super.onBackPressed();
	}

	//    @Override
	//    public boolean onKeyDown(int keyCode, KeyEvent event) {
	//        Toast.makeText(this, "KeyCode:" + keyCode, Toast.LENGTH_SHORT).show();
	//        if (keyCode == KeyEvent.KEYCODE_BACK) {
	//            return false;
	//        }
	//        return super.onKeyDown(keyCode, event);
	//    }

	//    @Override
	//    public boolean dispatchKeyEvent(KeyEvent event) {
	////        return super.dispatchKeyEvent(event);
	//        return false;
	//    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, 1, 1, "内存管理");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case 1:
				startActivity(new Intent(MainActivity.this, ActivityLeakcanary.class));
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		ResolveInfo res = mAllApps.get(position);
		String pkg = res.activityInfo.packageName;//应用包名
		if (pkg.equals("hw.ipsp.jcw.app"))
		{
			uninstallAPK(pkg);
		} else
		{
			Toast.makeText(MainActivity.this, "程序不可卸载", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	private void uninstallAPK(String packageName)
	{
		Uri uri = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		this.startActivity(intent);
	}

	//    @Override
	//    public void onWindowFocusChanged(boolean hasFocus) {
	//        super.onWindowFocusChanged(hasFocus);
	//        try {
	//            Object service = getSystemService("statusbar");
	//            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
	//            Method test = statusbarManager.getMethod("collapse");
	//            test.invoke(service);
	//        } catch (Exception ex) {
	//            ex.printStackTrace();
	//        }
	//    }
	//    @Override
	//    public void onWindowFocusChanged(boolean hasFocus) {
	//        // TODO Auto-generated method stub
	//        super.onWindowFocusChanged(hasFocus);
	//        sendBroadcast(new Intent("Android.intent.action.CLOSE_SYSTEM_DIALOGS"));
	//    }

	//    @Override
	//    public boolean onTouchEvent(MotionEvent event) {
	//        switch (event.getAction()) {
	//            case MotionEvent.ACTION_MOVE:
	//                return true;
	//            case MotionEvent.ACTION_DOWN:
	//                break;
	//        }
	//        return super.onTouchEvent(event);
	//    }

	//    @Override
	//    public void onWindowFocusChanged(boolean hasFocus) {
	//        super.onWindowFocusChanged(hasFocus);
	//        try {
	//            Object service = getSystemService("statusbar");
	//            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
	//            Method test = statusbarManager.getMethod("collapse");
	//            test.invoke(service);
	//        } catch (Exception ex) {
	//            ex.printStackTrace();
	//        }
	//    }

	//    @Override
	//    public void onWindowFocusChanged(boolean hasFocus) {
	//        disableStatusBar();
	//        super.onWindowFocusChanged(hasFocus);
	//    }
	//
	//    public void disableStatusBar() {
	//        try {
	//            Object service = getSystemService("statusbar");
	//            Class<?> claz = Class.forName("android.app.StatusBarManager");
	//            Method expand = claz.getMethod("collapse");
	//            expand.invoke(service);
	//        } catch (Exception e) {
	//            e.printStackTrace();
	//        }
	//    }
}

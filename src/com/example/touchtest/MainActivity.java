package com.example.touchtest;

import android.os.Bundle;
import android.app.Activity;
import android.text.GetChars;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements TouchTestResultListener {
	private static final String TAG = MainActivity.class.getName();
	DrawView view;
	private TextView startLeft;
	private TextView startRight;
	private TextView endLeft;
	private TextView endRight;
	private int step;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);// 将view视图放到Activity中显示
		view = (DrawView) findViewById(R.id.draw_view);
		view.setTouchTestResultListener(this);
		startLeft = (TextView) findViewById(R.id.start_left);
		startRight = (TextView) findViewById(R.id.start_right);

		endLeft = (TextView) findViewById(R.id.end_left);
		endRight = (TextView) findViewById(R.id.end_right);

	}

	@Override
	protected void onResume() {
		super.onResume();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int[] position1 = new int[2];
		startLeft.getLocationOnScreen(position1);
		int[] position2 = new int[2];
		endRight.getLocationOnScreen(position2);
		//view.setStartEnd(position1[0]+startLeft.getWidth(), position1[1]+startLeft.getHeight(), position2[0], position2[1]);
		view.clear();
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onTouchTestResultOK(boolean passed) {

		Log.d(TAG, "onTouchTestResultOK passed="+passed);
		if (passed) {
			step++;
			startLeft.setVisibility(View.GONE);
			startRight.setVisibility(View.VISIBLE);
			endLeft.setVisibility(View.VISIBLE);
			endRight.setVisibility(View.GONE);
			
			int[] position1 = new int[2];
			startRight.getLocationOnScreen(position1);
			int[] position2 = new int[2];
			endLeft.getLocationOnScreen(position2);
			//view.setStartEnd(position1[0], position1[1]+startRight.getHeight(), position2[0]+endLeft.getWidth(), position2[1]);
			
			
		} else {
			//this.finish();
		}

	}

}

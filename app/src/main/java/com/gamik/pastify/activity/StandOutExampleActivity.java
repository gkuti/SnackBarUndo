package com.gamik.pastify.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gamik.pastify.activity.OverLayIconActivity;

import wei.mark.standout.StandOutWindow;

public class StandOutExampleActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//StandOutWindow.closeAll(this, OverLayListActivity.class);
//		StandOutWindow.closeAll(this, MultiWindow.class);
//		StandOutWindow.closeAll(this, WidgetsWindow.class);

		// show a MultiWindow, OverLayListActivity

		StandOutWindow
				.show(this, OverLayIconActivity.class, StandOutWindow.DEFAULT_ID);
//		StandOutWindow.show(this, MultiWindow.class, StandOutWindow.DEFAULT_ID);
//		StandOutWindow.show(this, WidgetsWindow.class,
//				StandOutWindow.DEFAULT_ID);

		// show a MostBasicWindow. It is commented out because it does not
		// support closing.

		/*
		 * StandOutWindow.show(this, StandOutMostBasicWindow.class,
		 * StandOutWindow.DEFAULT_ID);
		 */

		finish();
	}
}
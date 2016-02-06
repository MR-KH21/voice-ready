package voice.khouli.com.ready;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LifeCycleListener implements Application.ActivityLifecycleCallbacks{
	protected static final int REQUEST_OK = 1;
	ActivityActionsManager activityActionsManager;
	ShakeEventListener mSensorListener;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 5000;
	private long mShakeTimestamp;

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		activityActionsManager = new ActivityActionsManager(activity);
	}

	@Override
	public void onActivityStarted(Activity activity) {

	}


	@Override
	public void onActivityResumed(final Activity activity) {
		activityActionsManager.scanActions();
		ShakeEventListener mSensorListener = new ShakeEventListener(activity, new ShakeEventListener.OnShakeListener() {
			@Override
			public void onShake() {
				if (System.currentTimeMillis() - mShakeTimestamp > SHAKE_COUNT_RESET_TIME_MS) {
					triggerRecognizerIntent(activity);
					mShakeTimestamp = System.currentTimeMillis();
				}
			}
		});

		mSensorListener.registerShakeEventListener();

		VoiceOverlayer overlayer = new VoiceOverlayer(activity, new VoiceOverlayer.VoiceIconListener() {
			@Override
			public void onVoiceIconClicked(Activity activity) {
				triggerRecognizerIntent(activity);
			}
		});
		activity.addContentView(overlayer,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	@Override
	public void onActivityPaused(Activity activity) {
		if (mSensorListener != null) {
			mSensorListener.unregisterShakeEventListener();
		}
	}

	@Override
	public void onActivityStopped(Activity activity) {

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

	}

	@Override
	public void onActivityDestroyed(Activity activity) {

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode==REQUEST_OK  && resultCode == Activity.RESULT_OK) {
			ArrayList<String> voidAction = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//to do write the filter to remove common calls like "click on ..."
			String voiceAction = voidAction.get(0);
			activityActionsManager.callAction(voiceAction.toLowerCase());
		}
	}

	public void addVoiceAction(final String action ,final VoiceAction voiceAction){
		activityActionsManager.addVoiceAction(action,voiceAction);
	}

	private void triggerRecognizerIntent(final Activity activity){
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		try {
			activity.startActivityForResult(i, REQUEST_OK);
		} catch (Exception e) {
			Toast.makeText(activity, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
		}
	}
}

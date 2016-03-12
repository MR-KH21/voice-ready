package voice.khouli.com.ready;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceReadyLifeCycleListener implements Application.ActivityLifecycleCallbacks{
	protected static final int REQUEST_OK = 1;
	ActivityActionsManager activityActionsManager;
	ShakeEventListener mSensorListener;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 5000;
	private long mShakeTimestamp;
	private int triggerMode = 0;
	private int iconResourceID = 0 ;
	public VoiceReadyLifeCycleListener(int mode){
		triggerMode = mode;
	}

	public VoiceReadyLifeCycleListener(int mode , int iconResourceID){
		triggerMode = mode;
		this.iconResourceID = iconResourceID;
	}

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
		switch (triggerMode){
			case VoiceReady.VOICE_TRIGGER_FLOATING_BUTTON:
				addVoiceOverlayerButton(activity);
				break;
			case VoiceReady.VOICE_TRIGGER_SHAKING:
				addShakeListener(activity);
				break;
			case VoiceReady.VOICE_TRIGGER_ALL:
				addVoiceOverlayerButton(activity);
				addShakeListener(activity);
				break;

		}
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
			ArrayList<String> voiceActions = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//to do write the filter to remove common calls like "click on ..."
			String voiceAction = voiceActions.get(0);
			activityActionsManager.callAction(voiceAction.toLowerCase());
		}
	}

	public void addVoiceAction(final String action ,final VoiceAction voiceAction){
		activityActionsManager.addVoiceAction(action,voiceAction);
	}

	private void triggerVoiceRecognizer(final Activity activity , SpeechRecognitionUtil.RecognitionActions actions){
		try {
			SpeechRecognitionUtil.recognizeSpeechDirectly(activity , actions);
		} catch (Exception e) {
			Toast.makeText(activity, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
		}
	}

	private void addVoiceOverlayerButton(final Activity activity){
		VoiceOverlayer overlayer = new VoiceOverlayer(activity, iconResourceID, new VoiceOverlayer.VoiceIconListener() {
			@Override
			public void onVoiceIconClicked(Activity activity) {
				triggerVoiceRecognizer(activity, new SpeechRecognitionUtil.RecognitionActions() {
					@Override
					public void onResults(ArrayList<String> voiceActions) {
						try {
							String voiceAction = voiceActions.get(0);
							activityActionsManager.callAction(voiceAction.toLowerCase());
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}
				});
			}
		});
		activity.addContentView(overlayer,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	private void addShakeListener(final Activity activity){
		ShakeEventListener mSensorListener = new ShakeEventListener(activity, new ShakeEventListener.OnShakeListener() {
			@Override
			public void onShake() {
				if (System.currentTimeMillis() - mShakeTimestamp > SHAKE_COUNT_RESET_TIME_MS) {
					triggerVoiceRecognizer(activity, new SpeechRecognitionUtil.RecognitionActions() {
						@Override
						public void onResults(ArrayList<String> voiceActions) {
							try {
								String voiceAction = voiceActions.get(0);
								activityActionsManager.callAction(voiceAction.toLowerCase());
							}catch (Exception ex){
								ex.printStackTrace();
							}
						}
					});
					mShakeTimestamp = System.currentTimeMillis();
				}
			}
		});
		mSensorListener.registerShakeEventListener();
	}

}

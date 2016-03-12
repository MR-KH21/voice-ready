package voice.khouli.com.ready;

import android.app.Application;
import android.content.Intent;

public class VoiceReady {
	public static final int VOICE_TAG = R.drawable.mic + 4 ;
	static boolean listening;
	public static final int  VOICE_TRIGGER_FLOATING_BUTTON = 1;
	public static final  int VOICE_TRIGGER_SHAKING = 2;
	public static final int VOICE_TRIGGER_ALL = 3;

	private static VoiceReadyLifeCycleListener voiceVoiceReadyLifeCycleListener;
	public static void init(final Application application , int voiceTrigger){
		voiceVoiceReadyLifeCycleListener = new VoiceReadyLifeCycleListener(voiceTrigger);
		application.registerActivityLifecycleCallbacks(voiceVoiceReadyLifeCycleListener);
	}


	public static void init(final Application application , int voiceTrigger, int micResourceDrawable){
		voiceVoiceReadyLifeCycleListener = new VoiceReadyLifeCycleListener(voiceTrigger,micResourceDrawable);
		application.registerActivityLifecycleCallbacks(voiceVoiceReadyLifeCycleListener);
	}

	public static void onActivityResult(int requestCode, int resultCode, Intent data){
		voiceVoiceReadyLifeCycleListener.onActivityResult(requestCode,resultCode,data);
	}


	public static void addCustomAction(String actionText,VoiceAction action){
		voiceVoiceReadyLifeCycleListener.addVoiceAction(actionText,action);
	}



}

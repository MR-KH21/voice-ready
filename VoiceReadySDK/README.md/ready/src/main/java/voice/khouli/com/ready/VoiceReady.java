package voice.khouli.com.ready;

import android.app.Application;
import android.content.Intent;

public class VoiceReady {

	public static int VOICE_TRIGGER_FLOATING_BUTTON = 1;
	public static int VOICE_TRIGGER_SHAKING = 2;

	private static LifeCycleListener voiceLifeCycleListener ;

	public static void init(final Application application , int VoiceTrigger){
		voiceLifeCycleListener = new LifeCycleListener();
		application.registerActivityLifecycleCallbacks(voiceLifeCycleListener);
	}


	public static void onActivityResult(int requestCode, int resultCode, Intent data){
		voiceLifeCycleListener.onActivityResult(requestCode,resultCode,data);
	}


	public static void addCustomAction(String actionText,VoiceAction action){
		voiceLifeCycleListener.addVoiceAction(actionText,action);
	}



}

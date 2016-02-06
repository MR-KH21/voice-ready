package voice.khouli.com.ready;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceReady {
	public static final int VOICE_TAG = R.drawable.mic + 4 ;
	static boolean listening;
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

package voice.khouli.com.ready;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class SpeechRecognitionUtil {

	private static final String TAG = "SpeechRecognitionUtil";
	public interface RecognitionActions{
		 void onResults(ArrayList<String> voiceAction);
	}

	public static void recognizeSpeechDirectly(final Context context,RecognitionActions actions) {
		final SpeechRecognizer recognizer = getSpeechRecognizer(context);
		final Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		if (!recognizerIntent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE)) {
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.dummy");
		}
		recognizer.setRecognitionListener(getRecognitionListener(actions));
		recognizer.startListening(recognizerIntent);
	}

	public static SpeechRecognizer getSpeechRecognizer(final Context context){
		return  SpeechRecognizer.createSpeechRecognizer(context);
	}

	public static RecognitionListener getRecognitionListener(final RecognitionActions actions){
		return new RecognitionListener() {
			@Override
			public void onReadyForSpeech(Bundle params) {
				Log.i("RES","onReadyForSpeech");
			}

			@Override
			public void onBeginningOfSpeech() {
				Log.i("RES","onBeginningOfSpeech");
			}

			@Override
			public void onRmsChanged(float rmsdB) {
				Log.i("RES","onRms");
			}

			@Override
			public void onBufferReceived(byte[] buffer) {
				Log.i("RES","onBuffer");
			}

			@Override
			public void onEndOfSpeech() {
				Log.i("RES","onEnd");
			}

			@Override
			public void onError(int error) {
				Log.i("RES","on error "+diagnoseErrorCode(error));
			}

			@Override
			public void onResults(Bundle results) {
				ArrayList<String> voiceAction = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				actions.onResults(voiceAction);

			}

			@Override
			public void onPartialResults(Bundle partialResults) {
				Log.i("RES","partial res");
			}

			@Override
			public void onEvent(int eventType, Bundle params) {
				Log.i("RES","on event");
			}
		};
	}

	public static String diagnoseErrorCode(int errorCode)
	{
		String message;
		switch (errorCode)
		{
			case SpeechRecognizer.ERROR_AUDIO:
				message = "Audio recording error";
				break;
			case SpeechRecognizer.ERROR_CLIENT:
				message = "Client side error";
				break;
			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
				message = "Insufficient permissions";
				break;
			case SpeechRecognizer.ERROR_NETWORK:
				message = "Network error";
				break;
			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				message = "Network timeout";
				break;
			case SpeechRecognizer.ERROR_NO_MATCH:
				message = "No match";
				break;
			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				message = "RecognitionService busy";
				break;
			case SpeechRecognizer.ERROR_SERVER:
				message = "error from server";
				break;
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				message = "No speech input";
				break;
			default:
				message = "Didn't understand, please try again.";
				break;
		}
		return message;
	}
}
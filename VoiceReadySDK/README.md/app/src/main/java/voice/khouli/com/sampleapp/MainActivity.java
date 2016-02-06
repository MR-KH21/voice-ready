package voice.khouli.com.sampleapp;

import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import voice.khouli.com.ready.VoiceAction;
import voice.khouli.com.ready.VoiceReady;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.showVoice).setTag(VoiceReady.VOICE_TAG,"hidden tag,you can listen");

		findViewById(R.id.showVoice).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Ok I'm Listening XD ", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.toto).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext() , "More what exactly :P",Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		VoiceReady.addCustomAction("change background", new VoiceAction() {
			@Override
			public void doAction() {
				Random rnd = new Random();
				int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
				findViewById(R.id.root).setBackgroundColor(color);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		VoiceReady.onActivityResult(requestCode,resultCode,data);
	}
}

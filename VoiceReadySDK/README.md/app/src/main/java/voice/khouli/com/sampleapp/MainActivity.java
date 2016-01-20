package voice.khouli.com.sampleapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	protected static final int REQUEST_OK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.showVoice).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
				try {
					startActivityForResult(i, REQUEST_OK);
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
			ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			((TextView)findViewById(R.id.res)).setText(thingsYouSaid.get(0));
		}
	}
}

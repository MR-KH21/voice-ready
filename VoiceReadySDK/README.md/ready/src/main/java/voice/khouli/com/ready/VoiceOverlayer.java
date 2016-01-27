package voice.khouli.com.ready;

import android.app.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class VoiceOverlayer extends FrameLayout {

	public interface VoiceIconListener {
		void onVoiceIconClicked(final Activity activity );
	}
	final Activity activity;
	final VoiceIconListener iconListener;
	public VoiceOverlayer(final Activity activity , VoiceIconListener iconListener) {
        super(activity);
		this.activity = activity;
		this.iconListener = iconListener;
		init();

	}

	private void init() {
		View.inflate(getContext(), R.layout.overlayer_mic, this);
		findViewById(R.id.voiceIcon).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iconListener.onVoiceIconClicked(activity);
			}
		});
	}



}
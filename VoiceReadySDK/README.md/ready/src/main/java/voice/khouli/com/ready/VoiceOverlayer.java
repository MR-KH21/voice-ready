package voice.khouli.com.ready;

import android.app.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class VoiceOverlayer extends FrameLayout {

	public interface VoiceIconListener {
		void onVoiceIconClicked(final Activity activity );
	}
	final Activity activity;
	final VoiceIconListener iconListener;
	public VoiceOverlayer(final Activity activity , int resourceId, VoiceIconListener iconListener) {
        super(activity);
		this.activity = activity;
		this.iconListener = iconListener;
		init(resourceId);

	}

	private void init(int resourceId) {
		View.inflate(getContext(), R.layout.overlayer_mic, this);
		final ImageView icon = (ImageView) findViewById(R.id.voiceIcon);
		if (resourceId != 0) icon.setImageResource(resourceId);
		icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iconListener.onVoiceIconClicked(activity);
			}
		});
	}



}
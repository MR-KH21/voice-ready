package voice.khouli.com.ready;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class ActivityActionsManager {

	final Activity mCurrentActivity;
	final private HashMap<String,VoiceAction> textActions = new HashMap<>();
	public ActivityActionsManager(final Activity activity){
		mCurrentActivity = activity;
	}


	private void scanViews(final View view) {
		//support tags later
		if (view.getTag(VoiceReady.VOICE_TAG) != null){
			try {
				String tagTxt = (String) view.getTag(VoiceReady.VOICE_TAG);
				String[] tagStrings = tagTxt.split(",");
				for (String action: tagStrings) {
					textActions.put(action, new VoiceAction() {
						@Override
						public void doAction() {
							view.callOnClick();
						}
					});
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		if (view instanceof TextView) {
			final TextView textView = (TextView) view;
			if (textView.getText() != null) {
				String text = textView.getText().toString();
				textActions.put(text.toLowerCase(), new VoiceAction() {
					@Override
					public void doAction() {
						view.callOnClick();
					}
				});
			}
		}
		if (view instanceof ViewGroup) {
			final ViewGroup viewGroup = (ViewGroup) view;
			for (int i = 0; i < viewGroup.getChildCount(); ++i) {
				scanViews(viewGroup.getChildAt(i));
			}
		}
	}

	public void scanActions(){
		scanViews(mCurrentActivity.getWindow().getDecorView());
	}

	public void callAction(String action){
		if (textActions.containsKey(action)){
			textActions.get(action).doAction();
		}
	}

	public void addVoiceAction(String action , VoiceAction voiceAction){
		if (textActions != null){
			textActions.put(action,voiceAction);
		}
	}


}

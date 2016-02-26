package voice.khouli.com.sampleapp;


import voice.khouli.com.ready.VoiceReady;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
		VoiceReady.init(this,VoiceReady.VOICE_TRIGGER_FLOATING_BUTTON);
    }
}

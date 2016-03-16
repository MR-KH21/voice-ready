# Voice-Ready


A library that will allow the voice interaction with app user by simply scanning all the text on the screen and wait the user to pronounce any word presented in the UI then trigger the onClick call of it. Moreover you can add your own custom voice actions to your app plus you can trigger an action of any view by simply set a tag string (plase check the example).


# Usage

1) Download the aar file provided in the master branch and imported in your android studio (File -> New Module -> import Jar/AAR package).
<br>
2) In your App add a module depedency to the previous imported module.
<br>
3) Create a custom application and refer to it in your manifest (check this <a href="http://www.devahead.com/blog/2011/06/extending-the-android-application-class-and-dealing-with-singleton/">link</a> ).
<br>

4) In your application class init the library by calling 
<br>
          VoiceReady.init(this,VoiceReady.VOICE_TRIGGER_ALL, R.drawable.icon_mic);
<br>     

5) if you want to add your custom actions you can add them by calling <br>
          VoiceReady.addCustomAction("change background", new VoiceAction() {
			@Override
			public void doAction() {
				Random rnd = new Random();
				int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
				findViewById(R.id.root).setBackgroundColor(color);
			}
		});
		<br>
		in your activity onResume() callback.
		





#License
Copyright (C) 2016 Mohanad KHOULI

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

#! /bin/bash

cordova create findLocal com.hcp.find Find
cd findLocal
cordova platform add android
cordova plugin add https://github.com/schollz/cordova-plugin-wifi.git
cordova plugin add cordova-plugin-whitelist
cordova plugin add https://github.com/schollz/cordova-plugin-background-mode.git
cordova plugin add cordova-plugin-dialogs
cordova plugin add cordova-plugin-powermanagement
#cordova plugin add https://github.com/schollz/phonegap-plugin-brightness.git
#cordova plugin add cordova-plugin-chrome-apps-alarms
#cordova plugin add https://github.com/uniclau/AlarmPlugin.git
#cordova plugin add cordova-plugin-vibration
cordova plugin add cordova-plugin-eddystone
cordova plugin add cordova-plugin-console
cp ../index.html platforms/android/assets/www/ && cp ../jquery-1.9.js platforms/android/assets/www/ && cp ../main.js platforms/android/assets/www/ && ./platforms/android/cordova/build

./platforms/android/cordova/run --device

cp ../appIcon.png platforms/android/res/drawable-hdpi/icon.png
cp ../appIcon.png platforms/android/res/drawable-mdpi/icon.png
cp ../appIcon.png platforms/android/res/drawable-ldpi/icon.png
cp ../appIcon.png platforms/android/res/drawable-xhdpi/icon.png



./platforms/android/cordova/build
./platforms/android/cordova/run --device
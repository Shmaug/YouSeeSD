# YouSeeSD

![logo](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/app/src/main/res/drawable/ic_launcher.png)

> Tour guide Android app targeting all ages of people.

The app `YouSeeSD` can auto-generate a high quality tour by just selecting a
list of keywords (a.k.a. tags). Then, you can start the tour any time as the app
stores your tour in your device. Our ap does not require an email to sign in as
it recognizes a user as an unique device. On tour mode, you can view the full map
around the campus and you are able to mark visited for places along the tour.

## Installation

- `Java 1.8` is used.
- Android Studio is required.
- `compileSDKVersion` is 28.

## Development setup

To fully test the functionalities at `OnTourActivity`, you have to register your
`SHA1` from your debug keystore, or, you are required to replace the Google Maps API key
with your own API key. Please go to this [file](https://github.com/Shmaug/YouSeeSD/blob/master/app/src/debug/res/values/google_maps_api.xml#L23) and replace:

Also, you will lose the ability to read/write data into the `Firebase` real-time database since
we do not recognize your `SHA1` from your debug keystore in our Firebase account. Either you ask us
to manually register your `SHA1` key, or you can just download the file `youseesd_firebase.json` in the root directory
in our project repo to set up your own firebase database. Then, also you have to replace the firebase API key by yourself.

## Getting Started

After you figured out the API keys for Google Maps and Firebase, then you are ready to run our project.

### Firebase: Register keystore

You first need to get your SHA1 fingerprint of certificate. To get your SHA1, simply run the following command

```
keytool -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore
```

This will use your `debug.keystore`, and the default password is just `android`. You will see the following as a result once you run the command

```
yonghoondo@Yongs-MacBook-Pro ~ » keytool -list -v \
-alias androiddebugkey -keystore ~/.android/debug.keystore
Enter keystore password:
Alias name: androiddebugkey
Creation date: Nov 27, 2018
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: C=US, O=Android, CN=Android Debug
Issuer: C=US, O=Android, CN=Android Debug
Serial number: 1
Valid from: Tue Nov 27 15:51:12 PST 2018 until: Thu Nov 19 15:51:12 PST 2048
Certificate fingerprints:
	 MD5:  75:D3:40:DF:8E:CC:DB:64:78:14:61:05:06:3C:BB:2C
	 SHA1: 44:A7:CE:CE:B0:4F:1C:51:64:FF:BA:A5:BB:5F:71:FB:EA:F9:C6:6B
	 SHA256: 48:9A:51:20:DF:32:71:4C:50:4A:71:C8:DC:01:E7:9D:D4:52:CE:BA:8E:2E:72:FB:93:4A:67:46:58:5D:1A:4D
Signature algorithm name: SHA1withRSA
Subject Public Key Algorithm: 1024-bit RSA key
Version: 1

Warning:
The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore /Users/yonghoondo/.android/debug.keystore -destkeystore /Users/yonghoondo/.android/debug.keystore -deststoretype pkcs12".
```

There, you only need the SHA1 key which is `SHA1: 44:A7:CE:CE:B0:4F:1C:51:64:FF:BA:A5:BB:5F:71:FB:EA:F9:C6:6B`.

You can find more details at https://developers.google.com/android/guides/client-auth

### How to setup the code style

Import the `codestyle/Square.xml` into Android Studio at `Preferences > Code Style > Java > Scheme`.

![CodeStyleGuide](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/codestyle/codestyle.png)

Then, go to [`Project Settings > Android App > YouSeeSD`](https://console.firebase.google.com/u/0/project/youseesd-9ab46/settings/general/android:com.beep.youseesd), and add your fingerprint.

![FingerprintFirebase](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/screenshots/fingerprint_firebase.png)

For more information, Square Java Code Styles is given at https://github.com/square/java-code-styles.

### How to reformat code

You can find the keymap in `Preferences > Keymap > Reformat code`.

![FormatCodeGuide](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/codestyle/formatcode.png)

## Contributers
| Name | Role | Email |
|------|------|-------|
| Jaehoon Choi | Quality Assurance Lead | jac318@ucsd.edu |
| Jason Tae Kwang Chung | Algorithm Specialist | tkc016@ucsd.edu |
| Jimmy Dang | Senior System Analyst | jid086@ucsd.edu |
| Luke Deerinck | Business Analysit |	ldeerinc@ucsd.edu	|
| Yong Do | Database Specialist | yhdo@ucsd.edu|
| Trevor Hedstrom | Software Development Lead |	tjhedstr@ucsd.edu	|
| Jasmine Hoang |	User Interface Specialist | jah191@ucsd.edu	|
| Michael Jaber	| Algorithm Specialist | mjjaber@ucsd.edu |
| Lisa Luo | Software Architect | llluo@ucsd.edu |
| Grant Rogers | Project Manager | grogers@ucsd.edu |

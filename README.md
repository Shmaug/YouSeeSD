# YouSeeSD

> Short blurb about what your product does.

## Usecase Diagram

![UsecaseDiagram](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/screenshots/usecase_diagram.jpeg)

## Shared Documents

> [Shared Team Folder](https://drive.google.com/drive/u/2/folders/1oPU8XP1Roqrq_Mw1K2Q1nAPO6X8Ggnh6)

* [Requirements](https://drive.google.com/drive/u/2/folders/1oPU8XP1Roqrq_Mw1K2Q1nAPO6X8Ggnh6)
* [Use cases](https://docs.google.com/document/d/1z8ji4NFVDm0iHea319vruY-p7nbdJgUSOLFyklI361o/edit?usp=sharing)
* [User story](https://docs.google.com/document/d/119KrlIXgkzI2CvsMbKuN--wdjRUIS0i8g_sWcm4_NP0/edit?usp=sharing)

## Getting Started

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

For more information, Square Java Code Styles is given at https://github.com/square/java-code-styles.

### How to reformat code

You can find the keymap in `Preferences > Keymap > Reformat code`.

![FormatCodeGuide](https://raw.githubusercontent.com/Shmaug/YouSeeSD/master/codestyle/formatcode.png)

## Contributers
| Name | Role | Email |
|------|------|-------|
| Jason Tae Kwang Chung | Algorithm Specialist | tkc016@ucsd.edu |
| Luke Deerinck | Business Analysit |	ldeerinc@ucsd.edu	|
| Jimmy Dang | Senior System Analyst | jid086@ucsd.edu |
| Yong Do | Database Specialist | yhdo@ucsd.edu|
| Trevor Hedstrom | Software Development Lead |	tjhedstr@ucsd.edu	|
| Jasmine Hoang |	User Interface Specialist | jah191@ucsd.edu	|
| Michael Jaber	| Algorithm Specialist | mjjaber@ucsd.edu |
| Lisa Luo | Software Architect | llluo@ucsd.edu |
| Grant Rogers | Project Manager | grogers@ucsd.edu |
| Jaehoon Choi | Quality Assurance Lead | jac318@ucsd.edu |

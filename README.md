# Android biometric for apps that use legacy support libraries
Android biometric library inspired from [androidx.biometric](https://developer.android.com/reference/androidx/biometric/package-summary) for non androidx apps.

## Features 

### canAuthenticate
```java
int error = BiometricManager.from(MainActivity.this).canAuthenticate();
if (error != BiometricManager.BIOMETRIC_SUCCESS) {
    /// Can't authenticate at all
}
```

### authenticate
```java
BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
...
}

BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
        .setTitle("Set the title to display.")
        .setSubtitle("Set the subtitle to display.")
        .setDescription("Set the description to display")
        .setNegativeButtonText("Negative Button")
        .build();

mBiometricPrompt.authenticate(promptInfo);
```

## Example App
Checkout and look at example app

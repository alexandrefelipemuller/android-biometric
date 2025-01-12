package com.exxbrain.android_biometric;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.exxbrain.android.biometric.BiometricManager;
import com.exxbrain.android.biometric.BiometricPrompt;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_authenticate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int error = BiometricManager.from(MainActivity.this).canAuthenticate();
                if (error != BiometricManager.BIOMETRIC_SUCCESS) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Can't Authenticate")
                            .setMessage("Error " + error)
                            .create()
                            .show();
                    return;
                }


                Executor executor;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    executor = MainActivity.this.getMainExecutor();
                } else {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    executor = new Executor() {
                        @Override
                        public void execute(@NonNull Runnable command) {
                            handler.post(command);
                        }
                    };
                }


                BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                            // Just negative button tap
                            return;
                        }
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Error")
                                .setMessage(errorCode + ": " + errString)
                                .create()
                                .show();

                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });

                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Set the title to display.")
                        .setSubtitle("Set the subtitle to display.")
                        .setDescription("Set the description to display")
                        //.setNegativeButtonText("Negative Button")
                        .setDeviceCredentialAllowed(true)
                        .setConfirmationRequired(true)
                        .build();

                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
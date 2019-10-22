package com.example.faceandfingerprintauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;


import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class FaceActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private Executor executor = new Executor() {
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication..")
                .setSubtitle("Please look at front of your device to verify your identity")
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(false)
                .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(FaceActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                BiometricPrompt.CryptoObject authenticatedCryptoObject =
                        result.getCryptoObject();
                Toast.makeText(getApplicationContext(),
                        "Authentication success", Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });


        biometricPrompt.authenticate(promptInfo);


    }
}

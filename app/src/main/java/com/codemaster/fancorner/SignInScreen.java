package com.codemaster.fancorner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SignInScreen extends AppCompatActivity {

    TextInputEditText mobileNumberTextInput;
    Button sendOTPBtn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mobileNumberTextInput = findViewById(R.id.mobileNumberTextInput);
        sendOTPBtn = findViewById(R.id.sendOtpButton);

        sendOTPBtn.setOnClickListener(v -> {
            Intent otpScreenIntent = new Intent(SignInScreen.this, OTPScreen.class);
            startActivity(otpScreenIntent);
        });
    }
}
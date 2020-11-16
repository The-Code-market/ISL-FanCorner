package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    String verificationCodeBySystem;
    String phoneNumberStr;
    Button verifyBtn;
    PinView OTPCodePinView;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //initialization
        verifyBtn = findViewById(R.id.verifyBtn);
        OTPCodePinView = findViewById(R.id.pinView);
        progressBar = findViewById(R.id.progressBar);

        //get intent values
        phoneNumberStr = getIntent().getStringExtra("PhoneNumber");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode(phoneNumberStr);

        //verifyBtn click listener
        verifyBtn.setOnClickListener(v -> {
            String code = OTPCodePinView.getText().toString();
            if (code.isEmpty() || code.length() < 6) {
                OTPCodePinView.setError("Wrong OTP..");
                OTPCodePinView.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);
        });
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Initialize phone auth callbacks
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(OTPScreen.this, task -> {
            if (task.isSuccessful()) {
                Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccountScreen.class);
                createAccountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(createAccountIntent);
            } else {
                Toast.makeText(OTPScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
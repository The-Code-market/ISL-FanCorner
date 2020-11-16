package com.codemaster.fancorner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInScreen extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    TextInputEditText mobileNumberTextInput;
    Button sendOTPBtn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //initialization
        mobileNumberTextInput = findViewById(R.id.mobileNumberTextInput);
        sendOTPBtn = findViewById(R.id.sendOtpButton);

        //sendOtp button clicked listener
        sendOTPBtn.setOnClickListener(v -> {
            Intent OTpScreenIntent = new Intent(SignInScreen.this, OTPScreen.class);
            OTpScreenIntent.putExtra("PhoneNumber", "+91" + mobileNumberTextInput.getText().toString());
            Log.i("here Sign In", String.valueOf(mobileNumberTextInput.getText()));
            startActivity(OTpScreenIntent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
}
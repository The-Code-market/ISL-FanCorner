package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.codemaster.fancorner.SharedPreference.SharedPreference;
import com.codemaster.fancorner.model.Messages;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference db;
    TextInputEditText msgBox;
    ImageView send, attach;
    String currentDate, currentTime;
    RecyclerView relativeLayout;
    private final List<Messages> messagesList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        msgBox = findViewById(R.id.msgbox);
        send = findViewById(R.id.sendIm);

        relativeLayout = findViewById(R.id.scrollFirst);
        attach = findViewById(R.id.attachFile);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        messageAdapter = new MessageAdapter(messagesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        relativeLayout.setLayoutManager(layoutManager);
        relativeLayout.setAdapter(messageAdapter);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mInterstitialAd.show();
                handler.postDelayed(this,60000);

            }
        },60000);



        send.setOnClickListener(view -> {
            sendMessageInfo();
            msgBox.getText().clear();

        });
        attach.setOnClickListener(v -> {
            AttachDialog attachDialog = new AttachDialog(ChatScreen.this);
            attachDialog.setCancelable(true);
            Window window = attachDialog.getWindow();
            window.setLayout(ChipGroup.LayoutParams.MATCH_PARENT, ChipGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
            attachDialog.show();
            attachDialog.cF.setOnClickListener(v1 -> {
                Intent intentFile = new Intent();
                intentFile.setAction(Intent.ACTION_GET_CONTENT);
                intentFile.setType("image/*");
                startActivityForResult(intentFile, 20);
                attachDialog.dismiss();
            });

            attachDialog.cG.setOnClickListener(v12 -> {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 2);
                attachDialog.dismiss();

            });
        });
    }

    private void sendMessageInfo() {
        String message = msgBox.getText().toString();
        String messageKey = db.child("Messages").push().getKey();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getApplicationContext(), "Please write a message first", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.getDefault());
        currentDate = simpleDateFormat.format(calendar.getTime());
        Calendar calendarTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        currentTime = simpleDateFormatTime.format(calendarTime.getTime());
        Messages messagesSent = new Messages(message, "t", mAuth.getUid(), currentTime, currentDate, SharedPreference.getUserName(getApplicationContext()), SharedPreference.getUserTeam(getApplicationContext()));
        db.child("Messages").child(messageKey).setValue(messagesSent);
        relativeLayout.getAdapter().notifyDataSetChanged();
        relativeLayout.smoothScrollToPosition(relativeLayout.getAdapter().getItemCount());
    }



    @Override
    protected void onStart() {
        super.onStart();
        db.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Messages messages = snapshot.getValue(Messages.class);
                    messagesList.add(messages);
                    messageAdapter.notifyDataSetChanged();
                    relativeLayout.smoothScrollToPosition(relativeLayout.getAdapter().getItemCount());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Intent sIntent = new Intent(ChatScreen.this, ImageSend.class);
            sIntent.putExtra("imageUri", data.getData().toString());
            startActivity(sIntent);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Intent sIntent = new Intent(ChatScreen.this, ImageSend.class);
            sIntent.putExtra("imageUri", data.getData().toString());
            startActivity(sIntent);
        }
    }
}

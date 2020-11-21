package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemaster.fancorner.model.Messages;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreen extends AppCompatActivity {
    FirebaseAuth mauth;
    DatabaseReference db;
    TextInputEditText msgBox;
    ImageView send,attach;
    String currentDate,currentTime,userName;
    RecyclerView relativeLayout;

    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        msgBox=findViewById(R.id.msgbox);
        send=findViewById(R.id.sendIm);

        relativeLayout=findViewById(R.id.scrollFirst);
        attach=findViewById(R.id.attachFile);
        mauth=FirebaseAuth.getInstance();

        db= FirebaseDatabase.getInstance().getReference();
        messageAdapter=new MessageAdapter(messagesList);
        layoutManager=new LinearLayoutManager(this);
        relativeLayout.setLayoutManager(layoutManager);
        relativeLayout.setAdapter(messageAdapter);


        send.setOnClickListener(view -> {
            sendMessageInfo();
            msgBox.getText().clear();

        });
        attach.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AttachDialog attachDialog=new AttachDialog(ChatScreen.this);
                attachDialog.setCancelable(true);
                Window window=attachDialog.getWindow();
                window.setLayout(ChipGroup.LayoutParams.FILL_PARENT, ChipGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
                attachDialog.show();
                attachDialog.cF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentf = new Intent();
                        intentf.setAction(Intent.ACTION_GET_CONTENT);
                        intentf.setType( "image/*");
                        startActivityForResult(intentf,20);
                        attachDialog.dismiss();
                    }
                });

                attachDialog.cG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                       startActivityForResult(galleryIntent,2);
                        attachDialog.dismiss();

                    }
                });
            }
        });
    }
    private void sendMessageInfo(){
        String message=msgBox.getText().toString();
        String messageKey=db.child("Messages").push().getKey();

        if(TextUtils.isEmpty(message)){
            Toast.makeText(getApplicationContext(),"Please write a message first",Toast.LENGTH_SHORT);
        }
        else {

            db.child("users").child(mauth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        userName =snapshot.child("userName").getValue().toString();
                    }
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
                    currentDate=simpleDateFormat.format(calendar.getTime());
                    Calendar calendart=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormatt=new SimpleDateFormat("hh:mm a");
                    currentTime=simpleDateFormatt.format(calendart.getTime());
                    HashMap<String,Object> msgKey=new HashMap<>();
                    msgKey.put("name",userName);
                    msgKey.put("message",message);
                    msgKey.put("date",currentDate);
                    msgKey.put("time",currentTime);
                    msgKey.put("ud",mauth.getUid());
                    msgKey.put("type","t");
                    db.child("Messages").child(messageKey).updateChildren(msgKey);
                    relativeLayout.getAdapter().notifyDataSetChanged();
                    relativeLayout.smoothScrollToPosition(relativeLayout.getAdapter().getItemCount());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private String teamCheck(String s){

        final String[] teamName = new String[1];

        db.child("Users").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    teamName[0] =snapshot.child("team").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return teamName[0];


    }
@Override
    protected void onStart() {
            super.onStart();
        db.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Messages messages=snapshot.getValue(Messages.class);
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
            Intent sintent = new Intent(ChatScreen.this, ImageSend.class);
            sintent.putExtra("imageUri", data.getData().toString());
            startActivity(sintent);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Intent sintent = new Intent(ChatScreen.this, ImageSend.class);
            sintent.putExtra("imageUri", data.getData().toString());
            startActivity(sintent);
        }

    }

}
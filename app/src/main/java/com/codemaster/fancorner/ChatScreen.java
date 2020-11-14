package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class ChatScreen extends AppCompatActivity {
    FirebaseAuth mauth;
    DatabaseReference db;
    TextInputEditText msgBox;
    ImageView send;
    String currentDate,currentTime,userName;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        msgBox=findViewById(R.id.msgbox);
        send=findViewById(R.id.sendIm);
        scrollView=findViewById(R.id.scroller);
        mauth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference();
        db.child("Users").child(mauth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userName=snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageInfo();
                msgBox.getText().clear();

            }
        });
    }

    private void sendMessageInfo(){
        String message=msgBox.getText().toString();
        if(TextUtils.isEmpty(message)){
            Toast.makeText(getApplicationContext(),"Please write a message first",Toast.LENGTH_SHORT);
        }
        else {
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
            db.child("Messages").child(mauth.getUid()).updateChildren(msgKey);

            }
    }

private void DisplayMessages(DataSnapshot dataSnapshot){
    Iterator iterator=dataSnapshot.getChildren().iterator();
    while (iterator.hasNext()){
        String chatDate=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatMessage=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatTime=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatUsername=(String) ((DataSnapshot)iterator.next()).getValue();
        View view;
        
    }
}
}
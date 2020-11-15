package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreen extends AppCompatActivity {
    FirebaseAuth mauth;
    DatabaseReference db;
    TextInputEditText msgBox;
    ImageView send;
    String currentDate,currentTime,userName;
    ScrollView scrollView;
    TextView usName,message,date,time,sdate,stime,smsg;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        msgBox=findViewById(R.id.msgbox);
        send=findViewById(R.id.sendIm);
        scrollView=findViewById(R.id.scroller);
        mauth=FirebaseAuth.getInstance();
        usName=viewR.findViewById(R.id.msgUser);
        time=viewR.findViewById(R.id.msgTime);
        date=viewR.findViewById(R.id.msgDate);
        message=viewR.findViewById(R.id.msgM);
        smsg=viewS.findViewById(R.id.mssgM);
        sdate=viewS.findViewById(R.id.mssgDate);
        stime=viewS.findViewById(R.id.mssgTime);
        circleImageView=viewR.findViewById(R.id.profileRec_image);
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
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
    }

@SuppressLint("ResourceType")
private void DisplayMessages(DataSnapshot dataSnapshot){
    Iterator iterator=dataSnapshot.getChildren().iterator();
    while (iterator.hasNext()){
        String chatDate=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatMessage=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatTime=(String) ((DataSnapshot)iterator.next()).getValue();
        String chatUsername=(String) ((DataSnapshot)iterator.next()).getValue();
        LayoutInflater inflaterR =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         viewR = inflaterR.inflate(R.layout.messageiew, null);
        LayoutInflater inflaterS =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewS = inflaterS.inflate(R.layout.senderview, null);


        if(mauth.getUid().equals(((DataSnapshot) iterator.next()).getKey())){
            smsg.setText(chatMessage);
            sdate.setText(chatDate);
            stime.setText(chatTime);
            scrollView.addView(viewS);

        }
        else {
            circleImageView.setImageResource(R.drawable.common_full_open_on_phone);
            usName.setText(chatUsername);
            date.setText(chatDate);
            time.setText(chatTime);
            message.setText(chatMessage);
            scrollView.addView(viewR);

        }
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
}

    @Override
    protected void onStart() {
        super.onStart();
        db.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    DisplayMessages(snapshot);
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
    View viewS,viewR;
}
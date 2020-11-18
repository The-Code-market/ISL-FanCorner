package com.codemaster.fancorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Messages> messagesList;
    private FirebaseAuth mm;
    private DatabaseReference dab;
    public MessageAdapter(List<Messages> messagesList){
        this.messagesList=messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.messageiew,parent,false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int i) {
        mm=FirebaseAuth.getInstance();
        dab=FirebaseDatabase.getInstance().getReference();
        String currentUserId=mm.getCurrentUser().getUid();
        Messages messages=messagesList.get(i);
        String chatMessage=messages.getMessage();
        String chatType=messages.getType();
        String chatDate=messages.getDate();
        String chatTime=messages.getTime();
        String chatName=messages.getName();
        String chatUid=messages.getUd();

        if(currentUserId.equals(chatUid)) {
            holder.sLay.setVisibility(View.VISIBLE);

            holder.rLay.setVisibility(View.GONE);
            holder.circleImageView.setVisibility(View.GONE);
            holder.stime.setText(chatTime);
            holder.sdate.setText(chatDate);
            holder.senderMessage.setText(chatMessage);


       }
        else {
            holder.sLay.setVisibility(View.GONE);
            holder.rLay.setVisibility(View.VISIBLE);
            holder.rname.setText(chatName);
            holder.rtime.setText(chatTime);
            holder.rdate.setText(chatDate);
            holder.receiverMessage.setText(chatMessage);

        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView senderMessage,receiverMessage,rdate,rtime,sdate,stime,rname;
        public CircleImageView circleImageView;
        RelativeLayout sLay,rLay;
        public MessageViewHolder(@NonNull View view){

            super(view);
            receiverMessage=view.findViewById(R.id.msgM);
            circleImageView=view.findViewById(R.id.profileRec_image);
            senderMessage=view.findViewById(R.id.mssgM);
            rdate=view.findViewById(R.id.msgDate);
            rtime=view.findViewById(R.id.msgTime);
            stime=view.findViewById(R.id.mssgTime);
            sdate=view.findViewById(R.id.mssgDate);
            rname=view.findViewById(R.id.msgUser);
            sLay=view.findViewById(R.id.sLayout);
            rLay=view.findViewById(R.id.rLayout);


        }
    }
}

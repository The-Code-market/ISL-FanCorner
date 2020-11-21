package com.codemaster.fancorner;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codemaster.fancorner.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Messages> messagesList;
    private FirebaseAuth mm;
    private DatabaseReference dab;
    String teamName;
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
    if(chatType.equals("t"))
    {  if(currentUserId.equals(chatUid)) {
            holder.sLay.setVisibility(View.VISIBLE);
            holder.imgRL.setVisibility(View.GONE);
            holder.imgSL.setVisibility(View.GONE);
            holder.rLay.setVisibility(View.GONE);
            holder.circleImageView.setVisibility(View.GONE);
            holder.stime.setText(chatTime);
            holder.sdate.setText(chatDate);
            holder.senderMessage.setText(chatMessage);


       }
        else {
            holder.imgRL.setVisibility(View.GONE);
            holder.imgSL.setVisibility(View.GONE);
            holder.sLay.setVisibility(View.GONE);
            holder.rLay.setVisibility(View.VISIBLE);
            holder.circleImageView.setVisibility(View.VISIBLE);
            holder.rname.setText(chatName);
            holder.rtime.setText(chatTime);
            holder.rdate.setText(chatDate);
            holder.receiverMessage.setText(chatMessage);

            dab.child("users").child(chatUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        teamName= (String) snapshot.child("team").getValue();
                        if(teamName.equals("Kerala Blasters")){
                            holder.circleImageView.setImageResource(R.drawable.kerala);
                        }
                        else if(teamName.equals("ATK Mohun Bagan")){
                            holder.circleImageView.setImageResource(R.drawable.atkmb);
                        }
                        else if(teamName.equals("Odisha")){
                            holder.circleImageView.setImageResource(R.drawable.odisha);
                        }
                        else if(teamName.equals("Hyderbad")){
                            holder.circleImageView.setImageResource(R.drawable.hyderbad);
                        }
                        else if(teamName.equals("Bengaluru")){
                            holder.circleImageView.setImageResource(R.drawable.bengaluru);

                        }
                        else if(teamName.equals("Goa")){
                            holder.circleImageView.setImageResource(R.drawable.goa);

                        }
                        else if(teamName.equals("East Bengal")){
                            holder.circleImageView.setImageResource(R.drawable.eastbengal);

                        }
                        else if(teamName.equals("Jamshedpur")){
                            holder.circleImageView.setImageResource(R.drawable.jamshedpur);

                        }
                        else if(teamName.equals("Chennayin")){
                            holder.circleImageView.setImageResource(R.drawable.chennai);

                        }
                        else if(teamName.equals("Mumbai City")){
                            holder.circleImageView.setImageResource(R.drawable.mumbai);

                        }
                        else if(teamName.equals("NorthEast United")){
                            holder.circleImageView.setImageResource(R.drawable.northeast);

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
    else if(chatType.equals("i")){

        if(currentUserId.equals(chatUid)) {
            holder.sLay.setVisibility(View.GONE);
            holder.imgSL.setVisibility(View.VISIBLE);
            holder.rLay.setVisibility(View.GONE);
            holder.circleImageView.setVisibility(View.GONE);
            holder.imgRL.setVisibility(View.GONE);
            holder.imsTime.setText(chatTime);
            holder.imsDate.setText(chatDate);
            Picasso.get().load(chatMessage).into(holder.sIm);


        }
        else {
            holder.sLay.setVisibility(View.GONE);
            holder.rLay.setVisibility(View.GONE);
            holder.imgRL.setVisibility(View.VISIBLE);
            holder.imgSL.setVisibility(View.GONE);
            holder.imName.setText(chatName);
            holder.imrTime.setText(chatTime);
            holder.imrDate.setText(chatDate);
            holder.circleImageView.setVisibility(View.VISIBLE);
            Picasso.get().load(chatMessage).into(holder.rIm);

            dab.child("users").child(chatUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        teamName= (String) snapshot.child("team").getValue();
                        if(teamName.equals("Kerala Blasters")){
                            holder.circleImageView.setImageResource(R.drawable.kerala);
                        }
                        else if(teamName.equals("ATK Mohun Bagan")){
                            holder.circleImageView.setImageResource(R.drawable.atkmb);
                        }
                        else if(teamName.equals("Odisha")){
                            holder.circleImageView.setImageResource(R.drawable.odisha);
                        }
                        else if(teamName.equals("Hyderbad")){
                            holder.circleImageView.setImageResource(R.drawable.hyderbad);
                        }
                        else if(teamName.equals("Bengaluru")){
                            holder.circleImageView.setImageResource(R.drawable.bengaluru);

                        }
                        else if(teamName.equals("Goa")){
                            holder.circleImageView.setImageResource(R.drawable.goa);

                        }
                        else if(teamName.equals("East Bengal")){
                            holder.circleImageView.setImageResource(R.drawable.eastbengal);

                        }
                        else if(teamName.equals("Jamshedpur")){
                            holder.circleImageView.setImageResource(R.drawable.jamshedpur);

                        }
                        else if(teamName.equals("Chennayin")){
                            holder.circleImageView.setImageResource(R.drawable.chennai);

                        }
                        else if(teamName.equals("Mumbai City")){
                            holder.circleImageView.setImageResource(R.drawable.mumbai);

                        }
                        else if(teamName.equals("NorthEast United")){
                            holder.circleImageView.setImageResource(R.drawable.northeast);

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    }


    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView senderMessage,receiverMessage,rdate,rtime,sdate,stime,rname,imsDate,imrDate,imsTime,imrTime,imName;
        public CircleImageView circleImageView;
        ImageView sIm,rIm;
        RelativeLayout sLay,rLay,imgSL,imgRL;
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
            imgRL=view.findViewById(R.id.imageRec);
            imgSL=view.findViewById(R.id.imgSend);

            imrDate=view.findViewById(R.id.imgDate);
            imrTime=view.findViewById(R.id.imgTime);
            imsDate=view.findViewById(R.id.imgSdate);
            imsTime=view.findViewById(R.id.imgStime);
            imName=view.findViewById(R.id.imgUser);
            rIm=view.findViewById(R.id.imgView);
            sIm=view.findViewById(R.id.imgSview);



        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

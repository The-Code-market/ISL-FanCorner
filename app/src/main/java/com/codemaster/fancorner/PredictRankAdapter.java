package com.codemaster.fancorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codemaster.fancorner.model.RankPredictModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PredictRankAdapter extends RecyclerView.Adapter<PredictRankAdapter.RankViewHolder> {
    private List<RankPredictModel> rankList;
    private FirebaseAuth mi;
    private DatabaseReference ret;
    String cs1,cs2;
    public PredictRankAdapter(List<RankPredictModel> rankList){
        this.rankList=rankList;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pranklist,parent,false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int i) {
        mi=FirebaseAuth.getInstance();
        ret=FirebaseDatabase.getInstance().getReference();
        RankPredictModel ranks=rankList.get(i);
        String chatScore1=ranks.getScore1();
        String chatScore2=ranks.getScore2();
        String chatDate=ranks.getDate();
        String chatTime=ranks.getTime();
        String chatName=ranks.getName();
        String chatUid=ranks.getUd();
        String chatTeam=ranks.getTeam();
        ret.child("PredictionResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    cs1=snapshot.child("score1").getValue().toString();
                    cs2=snapshot.child("score2").getValue().toString();
                    if(chatScore1.equals(cs1) && chatScore2.equals(cs2)){
                        holder.name.setText(chatName);

                        if(("Kerala Blasters").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.kerala);
                        }
                        else if(("ATK Mohun Bagan").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.atkmb);
                        }
                        else if(("Odisha").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.odisha);
                        }
                        else if(("Hyderbad").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.hyderbad);
                        }
                        else if(("Bengaluru").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.bengaluru);

                        }
                        else if(("Goa").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.goa);

                        }
                        else if(("East Bengal").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.eastbengal);

                        }
                        else if(("Jamshedpur").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.jamshedpur);

                        }
                        else if(("Chennayin").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.chennai);

                        }
                        else if(("Mumbai City").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.mumbai);

                        }
                        else if(("NorthEast United").equals(chatTeam)){
                            holder.cRim.setImageResource(R.drawable.northeast);

                        }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        }




    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public CircleImageView cRim;
        public RankViewHolder(@NonNull View view){

            super(view);

            cRim=view.findViewById(R.id.rankPIm);
            name=view.findViewById(R.id.rankPUser);







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
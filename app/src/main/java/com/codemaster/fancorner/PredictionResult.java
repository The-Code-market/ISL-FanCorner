package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codemaster.fancorner.model.Messages;
import com.codemaster.fancorner.model.RankPredictModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PredictionResult extends AppCompatActivity {
    RecyclerView rankCycle;
    TextView warning;

    FirebaseAuth mit;
    DatabaseReference dt;
    private final List<RankPredictModel> rankList = new ArrayList<>();
    private PredictRankAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_result);
        rankCycle=findViewById(R.id.predictRank);
        warning=findViewById(R.id.textWarning);
        adapter = new PredictRankAdapter(rankList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rankCycle.setLayoutManager(layoutManager);
        rankCycle.setAdapter(adapter);
        dt= FirebaseDatabase.getInstance().getReference();
        mit=FirebaseAuth.getInstance();

        dt.child("PredictionResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    rankCycle.setVisibility(View.GONE);
                    warning.setVisibility(View.VISIBLE);
                }
                else {
                    rankCycle.setVisibility(View.VISIBLE);
                    warning.setVisibility(View.GONE);
                    loadAll();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


  private void loadAll(){
        dt.child("Predictions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    RankPredictModel rankPredictModel = snapshot.getValue(RankPredictModel.class);
                    rankList.add(rankPredictModel);
                    adapter.notifyDataSetChanged();

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

}
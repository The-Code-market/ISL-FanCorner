package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codemaster.fancorner.model.Messages;
import com.codemaster.fancorner.model.TeamRank;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRankScreen extends AppCompatActivity {
    DatabaseReference db;
    List<Messages> messagesList = new ArrayList<>();
    RecyclerView rankRecyclerView;
    RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rank_screen);

        rankRecyclerView = findViewById(R.id.rankRecycler);

        db = FirebaseDatabase.getInstance().getReference();
        getChat();
    }

    public void getChat() {
        db.child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Messages messages = childSnapshot.getValue(Messages.class);
                    messagesList.add(messages);
                }
                calculateRank(messagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void calculateRank(List<Messages> messagesList) {
        HashMap<String, Integer> rankMap = new HashMap<>();
        List<TeamRank> teamRank = new ArrayList<>();
        for (int i = 0; i < messagesList.size(); i++) {
            String teamCur = messagesList.get(i).getTeam();
            int count = rankMap.containsKey(teamCur) ? rankMap.get(teamCur) : 0;
            rankMap.put(teamCur, count + 1);
        }
        for (Map.Entry mapElement : rankMap.entrySet()) {
            String key = (String)mapElement.getKey();
            int value = (int)mapElement.getValue();
            teamRank.add(new TeamRank(key, value));
        }
        Collections.sort(teamRank);
        rankAdapter = new RankAdapter(teamRank);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rankRecyclerView.setLayoutManager(layoutManager);
        rankRecyclerView.setAdapter(rankAdapter);
    }
}
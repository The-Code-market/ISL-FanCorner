package com.codemaster.fancorner.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codemaster.fancorner.ChatRankScreen;
import com.codemaster.fancorner.R;
import com.google.android.material.card.MaterialCardView;

public class Home extends Fragment {
    MaterialCardView chatRankCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //initialization
        chatRankCard = view.findViewById(R.id.chatRank);

        chatRankCard.setOnClickListener(v -> {
            Intent chatRankIntent = new Intent(getActivity(), ChatRankScreen.class);
            startActivity(chatRankIntent);
        });
        return view;
    }
}
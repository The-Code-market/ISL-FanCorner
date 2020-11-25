package com.codemaster.fancorner.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.codemaster.fancorner.ChatScreen;
import com.codemaster.fancorner.R;


public class ChatFragment extends Fragment {


    RelativeLayout rL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        startActivity(new Intent(getContext(),ChatScreen.class));
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        rL=view.findViewById(R.id.chatFrag);
        rL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ChatScreen.class));
            }
        });
                return view;
    }
}
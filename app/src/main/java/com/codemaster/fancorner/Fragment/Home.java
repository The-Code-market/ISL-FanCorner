package com.codemaster.fancorner.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codemaster.fancorner.Prediction;
import com.codemaster.fancorner.R;

public class Home extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        startActivity(new Intent(getContext(), Prediction.class));
        return inflater.inflate(R.layout.fragment_home, container, false);



    }
}
package com.codemaster.fancorner.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codemaster.fancorner.BuildConfig;
import com.codemaster.fancorner.CreateAccountScreen;
import com.codemaster.fancorner.MainActivity;
import com.codemaster.fancorner.R;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {
    CardView personalInfoCard, shareCard, rateFeedBackCard;
    TextView userNameText, logoutText, roleText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //initialization
        userNameText = view.findViewById(R.id.userNameText);
        logoutText = view.findViewById(R.id.logoutText);
        roleText = view.findViewById(R.id.teamText);
        personalInfoCard = view.findViewById(R.id.personalInfo);
        shareCard = view.findViewById(R.id.shareCard);
        rateFeedBackCard = view.findViewById(R.id.rateFeedBackCard);

        rateFeedBackCard.setOnClickListener(v -> {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(rateIntent);
        });

        shareCard.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ISL Fan Corner.");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        });


        return view;
    }
}
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemaster.fancorner.BuildConfig;
import com.codemaster.fancorner.CreateAccountScreen;
import com.codemaster.fancorner.MainActivity;
import com.codemaster.fancorner.PersonalInfoEdit;
import com.codemaster.fancorner.R;
import com.codemaster.fancorner.SharedPreference.SharedPreference;
import com.codemaster.fancorner.SignInScreen;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    CardView personalInfoCard, shareCard, rateFeedBackCard, getHelpCard;
    TextView userNameText, logoutText, roleText;
    CircleImageView profileImg;

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
        getHelpCard = view.findViewById(R.id.getHelp);
        profileImg = view.findViewById(R.id.profilePic);

        // value load
        userNameText.setText(SharedPreference.getUserName(getContext()));
        roleText.setText(SharedPreference.getUserTeam(getContext()));

        // profile image setting
        String teamName = SharedPreference.getUserTeam(getContext());
        switch (teamName) {
            case "Kerala Blasters":
                profileImg.setImageResource(R.drawable.kerala);
                break;
            case "ATK Mohun Bagan":
                profileImg.setImageResource(R.drawable.atkmb);
                break;
            case "Odisha":
                profileImg.setImageResource(R.drawable.odisha);
                break;
            case "Hyderabad":
                profileImg.setImageResource(R.drawable.hyderbad);
                break;
            case "Bengaluru":
                profileImg.setImageResource(R.drawable.bengaluru);
                break;
            case "Goa":
                profileImg.setImageResource(R.drawable.goa);
                break;
            case "East Bengal":
                profileImg.setImageResource(R.drawable.eastbengal);
                break;
            case "Jamshedpur":
                profileImg.setImageResource(R.drawable.jamshedpur);
                break;
            case "Chennayin":
                profileImg.setImageResource(R.drawable.chennai);
                break;
            case "Mumbai City":
                profileImg.setImageResource(R.drawable.mumbai);
                break;
            case "NorthEast United":
                profileImg.setImageResource(R.drawable.northeast);
                break;
        }

        personalInfoCard.setOnClickListener(v -> {
            Intent personalInfoIntent = new Intent(getActivity(), PersonalInfoEdit.class);
            startActivity(personalInfoIntent);
        });

        getHelpCard.setOnClickListener(v -> {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(rateIntent);
        });

        rateFeedBackCard.setOnClickListener(v -> {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(rateIntent);
        });

        shareCard.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "12th Man.");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreference.setUserName(getContext(), "");
                SharedPreference.setUserTeam(getContext(), "");
                SharedPreference.setUserVerified(getContext(), false);
                startActivity(new Intent(getActivity(), SignInScreen.class));
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // value load
        userNameText.setText(SharedPreference.getUserName(getContext()));
        roleText.setText(SharedPreference.getUserTeam(getContext()));

        // profile image setting
        String teamName = SharedPreference.getUserTeam(getContext());
        switch (teamName) {
            case "Kerala Blasters":
                profileImg.setImageResource(R.drawable.kerala);
                break;
            case "ATK Mohun Bagan":
                profileImg.setImageResource(R.drawable.atkmb);
                break;
            case "Odisha":
                profileImg.setImageResource(R.drawable.odisha);
                break;
            case "Hyderabad":
                profileImg.setImageResource(R.drawable.hyderbad);
                break;
            case "Bengaluru":
                profileImg.setImageResource(R.drawable.bengaluru);
                break;
            case "Goa":
                profileImg.setImageResource(R.drawable.goa);
                break;
            case "East Bengal":
                profileImg.setImageResource(R.drawable.eastbengal);
                break;
            case "Jamshedpur":
                profileImg.setImageResource(R.drawable.jamshedpur);
                break;
            case "Chennayin":
                profileImg.setImageResource(R.drawable.chennai);
                break;
            case "Mumbai City":
                profileImg.setImageResource(R.drawable.mumbai);
                break;
            case "NorthEast United":
                profileImg.setImageResource(R.drawable.northeast);
                break;
        }
    }
}
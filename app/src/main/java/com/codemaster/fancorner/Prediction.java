package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codemaster.fancorner.SharedPreference.SharedPreference;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Prediction extends AppCompatActivity implements RewardedVideoAdListener {

    TextView team1, team2, matchNo;
    TextInputEditText team1Score, team2Score;
    CircleImageView team1Image, team2Image;
    Button submitButton, resultButton;
    String score1, score2, match, team1Of, team2Of, dateP, timeP, uName, team;
    FirebaseAuth faith;
    DatabaseReference ter;
    private RewardedVideoAd mRewardedVideoAd;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        faith = FirebaseAuth.getInstance();
        ter = FirebaseDatabase.getInstance().getReference();
        team1 = findViewById(R.id.team1Name);
        team2 = findViewById(R.id.team2Name);
        matchNo = findViewById(R.id.matchNo);
        team1Score = findViewById(R.id.team1Goal);
        team2Score = findViewById(R.id.team2Goal);
        team1Image = findViewById(R.id.team1Image);
        team2Image = findViewById(R.id.team2Image);
        submitButton = findViewById(R.id.submitPrediction);
        resultButton = findViewById(R.id.resultPrediction);
        alertDialog = new AlertDialog.Builder(Prediction.this).setTitle("Confirmation")
                .setMessage("Watch a video to confirm ,do you want to continue?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(mRewardedVideoAd.isLoaded()){
                            mRewardedVideoAd.show();
                        }
                        else {
                            loadRewardedVideoAd();
                            mRewardedVideoAd.show();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .create();


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        ter.child("PredictionStart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    match = snapshot.child("match").getValue().toString();
                    team1Of = snapshot.child("team1").getValue().toString();
                    team2Of = snapshot.child("team2").getValue().toString();
                    team1.setText(team1Of);
                    matchNo.setText(match);
                    team2.setText(team2Of);
                    resultButton.setVisibility(View.GONE);
                    if (team1Of.equals("Kerala Blasters")) {
                        team1Image.setImageResource(R.drawable.kerala);
                    } else if (team1Of.equals("ATK Mohun Bagan")) {
                        team1Image.setImageResource(R.drawable.atkmb);
                    } else if (team1Of.equals("Odisha")) {
                        team1Image.setImageResource(R.drawable.odisha);
                    } else if (team1Of.equals("Hyderbad")) {
                        team1Image.setImageResource(R.drawable.hyderbad);
                    } else if (team1Of.equals("Bengaluru")) {
                        team1Image.setImageResource(R.drawable.bengaluru);

                    } else if (team1Of.equals("Goa")) {
                        team1Image.setImageResource(R.drawable.goa);

                    } else if (team1Of.equals("East Bengal")) {
                        team1Image.setImageResource(R.drawable.eastbengal);

                    } else if (team1Of.equals("Jamshedpur")) {
                        team1Image.setImageResource(R.drawable.jamshedpur);

                    } else if (team1Of.equals("Chennayin")) {
                        team1Image.setImageResource(R.drawable.chennai);

                    } else if (team1Of.equals("Mumbai City")) {
                        team1Image.setImageResource(R.drawable.mumbai);

                    } else if (team1Of.equals("NorthEast United")) {
                        team1Image.setImageResource(R.drawable.northeast);

                    }
                    //

                    if (team2Of.equals("Kerala Blasters")) {
                        team2Image.setImageResource(R.drawable.kerala);
                    } else if (team2Of.equals("ATK Mohun Bagan")) {
                        team2Image.setImageResource(R.drawable.atkmb);
                    } else if (team2Of.equals("Odisha")) {
                        team2Image.setImageResource(R.drawable.odisha);
                    } else if (team2Of.equals("Hyderbad")) {
                        team2Image.setImageResource(R.drawable.hyderbad);
                    } else if (team2Of.equals("Bengaluru")) {
                        team2Image.setImageResource(R.drawable.bengaluru);

                    } else if (team2Of.equals("Goa")) {
                        team2Image.setImageResource(R.drawable.goa);

                    } else if (team2Of.equals("East Bengal")) {
                        team2Image.setImageResource(R.drawable.eastbengal);

                    } else if (team2Of.equals("Jamshedpur")) {
                        team2Image.setImageResource(R.drawable.jamshedpur);

                    } else if (team2Of.equals("Chennayin")) {
                        team2Image.setImageResource(R.drawable.chennai);

                    } else if (team2Of.equals("Mumbai City")) {
                        team2Image.setImageResource(R.drawable.mumbai);

                    } else if (team2Of.equals("NorthEast United")) {
                        team2Image.setImageResource(R.drawable.northeast);

                    }

                }
                ter.child("Predictions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(faith.getUid()).exists()) {
                            submitButton.setVisibility(View.GONE);
                            team1Score.setVisibility(View.GONE);
                            team2Score.setVisibility(View.GONE);
                            resultButton.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Prediction time expired", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (team1Score.getText().toString().isEmpty() || team2Score.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please enter scores before submitting", Toast.LENGTH_SHORT);
                }
                else {

                alertDialog.show();
                    alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRed));
                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRedDark));

                }
            }

        });

                resultButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Prediction.this, PredictionResult.class));
                    }
                });

           }
        private void loadRewardedVideoAd() {
            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                    new AdRequest.Builder().build());
        }

            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {


                score1 = team1Score.getText().toString();
                score2 = team2Score.getText().toString();

                if (score1.isEmpty() || score2.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please enter scores before submitting", Toast.LENGTH_SHORT);
                } else {
                    team = SharedPreference.getUserTeam(getApplicationContext());
                    uName = SharedPreference.getUserName(getApplicationContext());
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                    dateP = simpleDateFormat.format(calendar.getTime());
                    Calendar calendart = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormatt = new SimpleDateFormat("hh:mm a");
                    timeP = simpleDateFormatt.format(calendart.getTime());
                    HashMap<String, Object> msgKey = new HashMap<>();
                    msgKey.put("score1", score1);
                    msgKey.put("score2", score2);
                    msgKey.put("date", dateP);
                    msgKey.put("team", team);
                    msgKey.put("name", uName);
                    msgKey.put("time", timeP);
                    msgKey.put("ud", faith.getUid());
                    ter.child("Predictions").child(faith.getUid()).updateChildren(msgKey).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            submitButton.setVisibility(View.GONE);
                            team1Score.setVisibility(View.GONE);
                            team2Score.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), "Submitted successfully", Toast.LENGTH_SHORT);

                        }
                    });
                }
            }






    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }




}
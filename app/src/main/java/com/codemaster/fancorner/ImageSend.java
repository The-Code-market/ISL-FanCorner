package com.codemaster.fancorner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.codemaster.fancorner.SharedPreference.SharedPreference;
import com.codemaster.fancorner.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ImageSend extends AppCompatActivity {
    Uri imageUri, i = null;
    String imu;
    ImageView imageDis, cropB;
    CardView sendIm;
    DatabaseReference fab;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    String currentDate1, currentTime1, downloadUrl;
    ProgressDialog dialog;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_send);
        sendIm = findViewById(R.id.sendImage);
        imageDis = findViewById(R.id.imageDisplay);
        cropB = findViewById(R.id.cropB);
        fab = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        imu = getIntent().getExtras().getString("imageUri");
        imageUri = Uri.parse(imu);
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Image sending");
        dialog.setMessage("please wait.....");
        cropB.setOnClickListener(v -> startCropImageActivity(imageUri));
        imageDis.setImageURI(imageUri);
        sendIm.setOnClickListener(v -> sendMessageImInfo());
        getWindow().setStatusBarColor(R.color.imseb);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    private void sendMessageImInfo() {
        dialog.show();
        Uri message;
        if (i == null) {
            message = imageUri;
        } else {
            message = i;
        }
        String messageKey = fab.child("Messages").push().getKey();

        if (message == null) {
            Toast.makeText(getApplicationContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }
        final StorageReference filePath = storageReference.child("imageMessages").child(fAuth.getUid()).child(messageKey);
        filePath.putFile(message).addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
            downloadUrl = uri.toString();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.getDefault());
            currentDate1 = simpleDateFormat.format(calendar.getTime());
            Calendar calendarTime = Calendar.getInstance();
            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            currentTime1 = simpleDateFormatTime.format(calendarTime.getTime());
            Messages messagesSent = new Messages(downloadUrl, "i", fAuth.getUid(), currentTime1, currentDate1, SharedPreference.getUserName(getApplicationContext()), SharedPreference.getUserTeam(getApplicationContext()));

            fab.child("Messages").child(messageKey).setValue(messagesSent).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    finish();
                    i = null;
                }
            });
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            try {
                i = result.getUri();
                imageDis.setImageURI(i);
            } catch (Exception e) {

            }


        }
    }
}
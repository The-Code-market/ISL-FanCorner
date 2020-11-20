package com.codemaster.fancorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ImageSend extends AppCompatActivity {
Uri imageUri,i=null;
String imu;
ImageView imageDis,cropB;
CardView sendIm;
DatabaseReference fab;
FirebaseAuth fauth;
StorageReference storageReference;
String currentDate1,currentTime1,userName1,downloadUrl;
ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_send);
        sendIm=findViewById(R.id.sendImage);
        imageDis=findViewById(R.id.imageDisplay);
        cropB=findViewById(R.id.cropB);
        fab= FirebaseDatabase.getInstance().getReference();
        fauth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        imu=getIntent().getExtras().getString("imageUri");
        imageUri=Uri.parse(imu);
        dialog=new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Image sending");
        dialog.setMessage("please wait.....");
        cropB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropImageActivity(imageUri);
            }
        });

        imageDis.setImageURI(imageUri);

        sendIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessageImInfo();

            }
        });

    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);

    }


    private void sendMessageImInfo(){
        dialog.show();
        Uri message;
        if(i==null){
            message=imageUri;
        }
        else {
            message=i;
        }
        String messageKey=fab.child("Messages").push().getKey();

        if(message==null){
            Toast.makeText(getApplicationContext(),"Please select an image first",Toast.LENGTH_SHORT);
        }
        else {
            final StorageReference filePath=storageReference.child("imageMessages").child(fauth.getUid()).child(messageKey);
            filePath.putFile(message).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            downloadUrl=uri.toString();

                            fab.child("users").child(fauth.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        userName1 = snapshot.child("userName").getValue().toString();
                                    }
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                                    currentDate1 = simpleDateFormat.format(calendar.getTime());
                                    Calendar calendart = Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormatt = new SimpleDateFormat("hh:mm a");
                                    currentTime1 = simpleDateFormatt.format(calendart.getTime());
                                    HashMap<String, Object> msgKey = new HashMap<>();
                                    msgKey.put("name", userName1);
                                    msgKey.put("message", downloadUrl);
                                    msgKey.put("date", currentDate1);
                                    msgKey.put("time", currentTime1);
                                    msgKey.put("ud", fauth.getUid());
                                    msgKey.put("type", "i");
                                    fab.child("Messages").child(messageKey).updateChildren(msgKey).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                dialog.dismiss();
                                                finish();
                                                i = null;
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

                        }
                    });




                }



        }






















    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            i=result.getUri();
            imageDis.setImageURI(i);
        }
    }
}
package com.codemaster.fancorner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

public class AttachDialog extends Dialog {
        public CardView cF,cG;
        Activity c;
    public AttachDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attachdialog);
        cF=findViewById(R.id.fileSelect);
        cG=findViewById(R.id.gallerySelect);



    }
}

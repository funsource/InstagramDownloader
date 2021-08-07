package com.hcr2bot.instagramvideosdownloaderlibrary;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hcr2bot.instagramvideosdownloader.InstaVideo;
import com.hcr2bot.instaimagesdownloader.InstagramImages;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnDownload, btnPaste;
    EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.downloadBtn);
        btnPaste = findViewById(R.id.pasteBtn);
        url = findViewById(R.id.igUrlTextInput);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                int itemCount = clipData.getItemCount();
                if(itemCount > 0)
                {
                    // Get source text.
                    ClipData.Item item = clipData.getItemAt(0);
                    String text = item.getText().toString();
                    // Set the text to target textview.
                    url.setText(text);
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidUrl(url.getText().toString()))
                {
                    WhatToDownload(url.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "URL is not valid!",  Toast.LENGTH_LONG).show();
                }
                url.getText().clear();
            }
        });
    }

    private void WhatToDownload(String url){
        if (url.contains("www.instagram.com/reel")) { // ini reel
            InstaVideo.downloadVideo(MainActivity.this, url);
        } else if (url.contains("www.instagram.com/tv")){ //ini igtv
            InstaVideo.downloadVideo(MainActivity.this, url);
        } else if (url.contains("www.instagram.com/p")) { // ini photo
            InstagramImages.downloadImage(MainActivity.this, url);
        } else {
            Toast.makeText(this, "URL is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isValidUrl(String urlToCheck) {
        try {
            new URL(urlToCheck).toURI();
            return true;
        }
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
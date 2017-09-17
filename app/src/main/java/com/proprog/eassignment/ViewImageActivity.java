package com.proprog.eassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;


public class ViewImageActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        String user = intent.getStringExtra("flag");
        if (user.equals("student")) {
            byte[] downloaded_image = intent.getByteArrayExtra("image");

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(downloaded_image);
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        } else if(user.equals("doctor")){
            byte[] downloaded_image = intent.getByteArrayExtra("image");

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(downloaded_image);
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}

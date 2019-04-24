package com.rangel.cameraapi2;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EditPhoto extends AppCompatActivity {


    Lienzo lienzo;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        lienzo = findViewById(R.id.lienzo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lienzo.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), lienzo.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
                if (imgSaved != null) {
                    Snackbar.make(view, "¡dibujo salvado correctamente en la galería!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "¡ERROR! La imagen no se ha podido guardar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }


            }
        });

        Bundle bundle = getIntent().getExtras();
        try {
            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(bundle.getString("direcionFile")));

            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(bundle.getString("direcionFile"))));

            String a = bundle.getString("direcionFile");
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
            lienzo.setBitmap(bitmap);
        } catch (IOException io) {
            io.printStackTrace();
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }


}



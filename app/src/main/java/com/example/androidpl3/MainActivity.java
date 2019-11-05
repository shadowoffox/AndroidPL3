package com.example.androidpl3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private TextView outputText;
    private Button loadFile;
    private  Button convert;
    private ImageView imageView;
    private Bitmap bitmap = null;
    private Uri imageUri;
    @SuppressLint({"WrongThread", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        loadFile.setOnClickListener(new TakeAPhoto());

        convert.setOnClickListener(new ConvertIt());

      /*  Single.create(new SingleOnSubscribe<String>() {

            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {

                loadFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 1);
                        if (!emitter.isDisposed()) {
                            emitter.onNext(s.toString());
                        }
                    }
                });

            }
        }).subscribe((Consumer<String>) s -> outputText.setText(s), new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {

                }
            }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
                
    }*/



    }
    private void initViews(){
        outputText = findViewById(R.id.text_loading);
        loadFile = findViewById(R.id.button);
        convert = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
    }

    public class TakeAPhoto implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (data !=null){
        if (data.getData() != null) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            outputText.setText(imageUri.toString());

        }
        }else{
        outputText.setText("You need take a Photo");}
    }


    public class ConvertIt implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            FileOutputStream out = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out = new FileOutputStream(new File(getExternalFilesDir("Downloads"),"test.png"));
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + getExternalFilesDir("Downloads"));
                bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                System.out.println("save it!");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

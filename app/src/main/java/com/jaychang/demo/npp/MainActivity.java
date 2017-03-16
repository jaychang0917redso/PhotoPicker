package com.jaychang.demo.npp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaychang.npp.NPhotoPicker;

public class MainActivity extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();
  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button pickPhotoButton = (Button) findViewById(R.id.pickPhotoButton);
    pickPhotoButton.setOnClickListener(view -> pickPhotosFromAlbum());

    Button takePhotoButton = (Button) findViewById(R.id.takePhotoButton);
    takePhotoButton.setOnClickListener(view -> takePhotoFromCamera());

    imageView = (ImageView) findViewById(R.id.imageView);
  }

  private void pickPhotosFromAlbum() {
    NPhotoPicker.with(this)
      .toolbarColor(R.color.colorPrimary)
      .statusBarColor(R.color.colorPrimary)
      .selectedBorderColor(R.color.colorPrimary)
      .selectedIcon(R.drawable.ic_add)
      .actionText(R.string.add)
      .columnCount(3)
      .limit(6)
      .showCamera(true)
      .pickMultiPhotos()
      .subscribe(uris -> {
        Log.d(TAG, "uri size: " + uris.size());
        Log.d(TAG, "uri: " + uris.get(0));
        Glide.with(MainActivity.this).load(uris.get(0)).into(imageView);
      });
  }

  private void takePhotoFromCamera() {
    NPhotoPicker.with(this)
      .takePhotoFromCamera()
      .subscribe(uri -> {
        Log.d(TAG, "uri: " + uri);
        Glide.with(MainActivity.this).load(uri).into(imageView);
      });
  }

}

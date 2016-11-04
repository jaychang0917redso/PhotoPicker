package com.jaychang.demo.npp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jaychang.npp.NPhotoPicker;
import com.jaychang.npp.Photo;

import java.util.List;

import static com.jaychang.npp.NPhotoPicker.REQUEST_PHOTO_PICKER;

public class MainActivity extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        pickPhotos();
      }
    });
  }

  private void pickPhotos() {
    NPhotoPicker.create()
      .toolbarColor(R.color.colorPrimary)
      .statusBarColor(R.color.colorPrimary)
      .selectedBorderColor(R.color.colorPrimary)
      .selectedIcon(R.drawable.ic_add)
      .actionText(R.string.add)
      .columnCount(3)
      .limit(6)
      .multiMode()
      .start(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_PHOTO_PICKER && resultCode == RESULT_OK) {
      List<Photo> photos = NPhotoPicker.getPickedPhotos(data);
      Log.i(TAG, "User selected " + photos.size() + " photos");
      for (Photo photo : photos) {
        Log.i(TAG, "Uri: " + photo.getUri());
      }
    }
  }
}

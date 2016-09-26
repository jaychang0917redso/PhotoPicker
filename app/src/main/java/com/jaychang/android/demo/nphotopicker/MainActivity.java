package com.jaychang.android.demo.nphotopicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jaychang.npp.NPhotoPicker;
import com.jaychang.npp.OnMultiPhotosPickListener;
import com.jaychang.npp.Photo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();
  public static final int CODE_PHOTO_PICKER = 10001;
  private NPhotoPicker nPhotoPicker;

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
    nPhotoPicker = NPhotoPicker.create()
      .toolbarColor(R.color.colorPrimary)
      .statusBarColor(R.color.colorPrimary)
      .selectedBorderColor(R.color.colorPrimary)
      .selectedIcon(R.drawable.ic_add)
      .actionText(R.string.add)
      .columnCount(3)
      .limit(12)
      .multiMode()
      .onMultiPhotosPicked(new OnMultiPhotosPickListener() {
        @Override
        public void onMultiPhotosPicked(List<Photo> photos) {
          for (Photo photo : photos) {
            Log.i(TAG, "URI: " + photo.getUri());
          }
        }
      });

    nPhotoPicker.pick(this, CODE_PHOTO_PICKER);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    nPhotoPicker.onActivityResult(requestCode, resultCode, data);
  }
}

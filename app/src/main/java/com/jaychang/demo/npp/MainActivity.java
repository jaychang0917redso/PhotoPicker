package com.jaychang.demo.npp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaychang.npp.NPhotoPicker;

import java.util.List;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();
  private ImageView imageView;

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

    imageView = (ImageView) findViewById(R.id.imageView);
  }

  private void pickPhotos() {
    NPhotoPicker.with(this)
      .toolbarColor(R.color.colorPrimary)
      .statusBarColor(R.color.colorPrimary)
      .selectedBorderColor(R.color.colorPrimary)
      .selectedIcon(R.drawable.ic_add)
      .actionText(R.string.add)
      .columnCount(3)
      .limit(6)
      .pickMultiPhotos()
      .subscribe(new Action1<List<Uri>>() {
        @Override
        public void call(List<Uri> uris) {
          Log.i(TAG, "Uri: " + uris.size());
          Log.i(TAG, "Uri: " + uris.get(0).toString());
          Glide.with(MainActivity.this).load(uris.get(0)).into(imageView);
        }
      });
  }

}

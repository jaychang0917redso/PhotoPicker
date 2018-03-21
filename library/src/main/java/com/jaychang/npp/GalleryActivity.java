package com.jaychang.npp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaychang.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class GalleryActivity extends AppCompatActivity {

  private static final int CELL_SPACING = 2;
  private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 5001;

  private static final String[] GALLERY_PROJECTION = new String[]{
    MediaStore.Images.Media._ID,
    MediaStore.Images.Media.BUCKET_ID,
    MediaStore.Images.Media.DATE_TAKEN
  };

  private int toolbarColor;
  private int toolbarTitleTextColor;
  private int statusBarColor;
  private int selectedBorderColor;
  private GradientDrawable selectedBorderDrawable;
  private int selectedIcon;
  private int actionText;
  private int limit;
  private int columnCount;
  private boolean showCamera;
  private boolean isSingleMode;

  private final ArrayList<Photo> selectedPhotos = new ArrayList<>();
  private GalleryCursorAdapter galleryCursorAdapter;
  private RecyclerView recyclerView;
  private KolToolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AppUtils.setStatusBarColor(this, android.R.color.transparent);
    AppUtils.setContentBehindStatusBar(this);
    checkPermissions();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(LangUtils.wrap(newBase));
  }

  private void checkPermissions() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
      == PackageManager.PERMISSION_GRANTED) {
      setupAfterGrantPermission();
    } else {
      ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
        REQUEST_WRITE_EXTERNAL_STORAGE);
    }
  }

  private void setupAfterGrantPermission() {
    setContentView(R.layout.npp_activity_gallery);
    init();
    getLoaderManager().initLoader(0, null, new GalleryLoaderCallback());
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[],
                                         int[] grantResults) {
    if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE &&
      grantResults.length > 0 &&
      grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      setupAfterGrantPermission();
    } else {
      finish();
    }
  }

  private void init() {
    toolbarColor = getIntent().getIntExtra(NPhotoPicker.EXTRA_TOOLBAR_COLOR, 0);
    toolbarTitleTextColor = getIntent().getIntExtra(NPhotoPicker.EXTRA_TOOLBAR_TITLE_COLOR, 0);
    statusBarColor = getIntent().getIntExtra(NPhotoPicker.EXTRA_STATUS_BAR_COLOR, 0);
    selectedBorderColor = getIntent().getIntExtra(NPhotoPicker.EXTRA_SELECTED_BORDER_COLOR, 0);
    selectedIcon = getIntent().getIntExtra(NPhotoPicker.EXTRA_SELECTED_ICON, 0);
    actionText = getIntent().getIntExtra(NPhotoPicker.EXTRA_ACTION_TEXT, 0);
    limit = getIntent().getIntExtra(NPhotoPicker.EXTRA_LIMIT, -1);
    columnCount = getIntent().getIntExtra(NPhotoPicker.EXTRA_COL_COUNT, 3);
    showCamera = getIntent().getBooleanExtra(NPhotoPicker.EXTRA_SHOW_CAMERA, true);
    isSingleMode = getIntent().getBooleanExtra(NPhotoPicker.EXTRA_IS_SINGLE_MODE, false);

    selectedBorderDrawable = new GradientDrawable();
    selectedBorderDrawable.setStroke(8, ContextCompat.getColor(this, selectedBorderColor));

    toolbar = (KolToolbar) findViewById(R.id.toolbar);
    toolbar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    toolbar.getRightTextView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        notifySelectedPhotos();
      }
    });
    toolbar.getRightTextView().setVisibility(View.GONE);

    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
    recyclerView.addItemDecoration(GridSpacingItemDecoration.newBuilder().spacing(CELL_SPACING).build());
    galleryCursorAdapter = new GalleryCursorAdapter(this, null);
    recyclerView.setAdapter(galleryCursorAdapter);
  }

  private void notifySelectedPhotos() {
    List<Uri> uris = Observable.from(selectedPhotos)
      .map(new Func1<Photo, Uri>() {
        @Override
        public Uri call(Photo photo) {
          return photo.getUri(GalleryActivity.this);
        }
      })
      .toList().toBlocking().single();

    NPhotoPicker.getInstance().onPhotosPicked(uris);

    finish();
  }

  private void openCamera() {
    Intent intent = new Intent(this, CameraHiddenActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
  }

  private void notifySelectedPhoto() {
    if (NPhotoPicker.getInstance().isSingleMode()) {
      NPhotoPicker.getInstance().onPhotoPicked(selectedPhotos.get(0).getUri(this));
    } else {
      NPhotoPicker.getInstance().onPhotosPicked(Collections.singletonList(selectedPhotos.get(0).getUri(this)));
    }

    finish();
  }

  private class GalleryLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
      String sort = MediaStore.Images.Media.DATE_TAKEN + " DESC";
      return new CursorLoader(GalleryActivity.this, baseUri, GALLERY_PROJECTION, null, null, sort);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
      galleryCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
      galleryCursorAdapter.swapCursor(null);
    }
  }

  private class GalleryCursorAdapter extends CursorRecyclerViewAdapter<PhotoViewHolder> {

    GalleryCursorAdapter(Context context, Cursor cursor) {
      super(context, cursor);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      Context context = parent.getContext();
      View view = LayoutInflater.from(context).inflate(R.layout.npp_cell_photo, parent, false);
      return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder viewHolder, Cursor cursor) {
      final int photoId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
      final Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(photoId));
      final Photo photo = new Photo(imageUri, photoId);

      Glide.with(viewHolder.itemView.getContext())
        .load(imageUri)
        .centerCrop()
        .dontAnimate()
        .into(viewHolder.photoView);

      if (isSelected(photo)) {
        viewHolder.selectedIconView.setImageResource(R.drawable.btn_gallery_tick_on);
      } else {
        viewHolder.selectedIconView.setImageResource(R.drawable.btn_gallery_tick_off);
      }

      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (isOverLimit() && !isSelected(photo)) {
            return;
          }

          if (isSelected(photo)) {
            selectedPhotos.remove(photo);
            viewHolder.selectedIconView.setImageResource(R.drawable.btn_gallery_tick_off);
          } else {
            selectedPhotos.add(photo);
            viewHolder.selectedIconView.setImageResource(R.drawable.btn_gallery_tick_on);
          }

          if (isSingleMode) {
            notifySelectedPhoto();
            return;
          }

          updateToolbar();
        }
      });
    }

    private boolean isOverLimit() {
      return limit != -1 && !isSingleMode && selectedPhotos.size() >= limit;
    }

    private boolean isSelected(Photo photo) {
      return selectedPhotos.contains(photo);
    }
  }

  private void updateToolbar() {
    if (selectedPhotos.size() <= 0) {
      toolbar.getRightTextView().setVisibility(View.GONE);
    } else {
      toolbar.getRightTextView().setVisibility(View.VISIBLE);
    }
  }

  private static class PhotoViewHolder extends RecyclerView.ViewHolder {
    ImageView photoView;
    ImageView selectedIconView;
    View borderView;
    View layerView;

    PhotoViewHolder(View itemView) {
      super(itemView);
      photoView = (ImageView) itemView.findViewById(R.id.photoView);
      selectedIconView = (ImageView) itemView.findViewById(R.id.selectedIconView);
      borderView = itemView.findViewById(R.id.borderView);
      layerView = itemView.findViewById(R.id.layerView);
    }
  }

}

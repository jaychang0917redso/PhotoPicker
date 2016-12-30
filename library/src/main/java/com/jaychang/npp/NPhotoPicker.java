package com.jaychang.npp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public class NPhotoPicker {

  static final String EXTRA_TOOLBAR_COLOR = "EXTRA_TOOLBAR_COLOR";
  static final String EXTRA_TOOLBAR_TITLE_COLOR = "EXTRA_TOOLBAR_TITLE_COLOR";
  static final String EXTRA_STATUS_BAR_COLOR = "EXTRA_STATUS_BAR_COLOR";
  static final String EXTRA_SELECTED_BORDER_COLOR = "EXTRA_SELECTED_BORDER_COLOR";
  static final String EXTRA_SELECTED_ICON = "EXTRA_SELECTED_ICON";
  static final String EXTRA_ACTION_TEXT = "EXTRA_ACTION_TEXT";
  static final String EXTRA_LIMIT = "EXTRA_LIMIT";
  static final String EXTRA_COL_COUNT = "EXTRA_COL_COUNT";
  static final String EXTRA_IS_SINGLE_MODE = "EXTRA_IS_SINGLE_MODE";

  private int toolbarColor;
  private int toolbarTitleTextColor;
  private int statusBarColor;
  private int selectedBorderColor;
  private int selectedIcon;
  private int actionText;
  private int limit;
  private int columnCount;
  private boolean isSingleMode;

  @SuppressLint("StaticFieldLeak")
  private static NPhotoPicker instance;
  private Context appContext;
  private PublishSubject photoEmitter;

  private NPhotoPicker(Context context) {
    appContext = context;

    int primaryColor = android.R.color.background_dark;
    toolbarColor = primaryColor;
    statusBarColor = primaryColor;
    selectedBorderColor = R.color.npp_border;
    selectedIcon = R.drawable.ic_photo_selected;
    actionText = R.string.npp_done;
    limit = -1;
    columnCount = 3;
    isSingleMode = false;
  }

  public static synchronized NPhotoPicker with(Context context) {
    if (instance == null) {
      instance = new NPhotoPicker(context.getApplicationContext());
    }
    return instance;
  }

  static NPhotoPicker getInstance() {
    return instance;
  }

  public NPhotoPicker toolbarColor(@ColorRes int toolbarColor) {
    this.toolbarColor = toolbarColor;
    return this;
  }

  public NPhotoPicker toolbarTitleTextColor(@ColorRes int toolbarTitleTextColor) {
    this.toolbarTitleTextColor = toolbarTitleTextColor;
    return this;
  }

  public NPhotoPicker statusBarColor(@ColorRes int statusBarColor) {
    this.statusBarColor = statusBarColor;
    return this;
  }

  public NPhotoPicker selectedBorderColor(@ColorRes int selectedBorderColor) {
    this.selectedBorderColor = selectedBorderColor;
    return this;
  }

  public NPhotoPicker selectedIcon(@DrawableRes int selectedIcon) {
    this.selectedIcon = selectedIcon;
    return this;
  }

  public NPhotoPicker actionText(@StringRes int actionText) {
    this.actionText = actionText;
    return this;
  }

  public NPhotoPicker limit(@IntRange(from = 1) int limit) {
    this.limit = limit;
    return this;
  }

  public NPhotoPicker columnCount(@IntRange(from = 2) int columnCount) {
    this.columnCount = columnCount;
    return this;
  }

  public Observable<Uri> pickSinglePhotoFromAlbum() {
    isSingleMode = true;
    photoEmitter = PublishSubject.create();
    startGalleryActivity();
    return photoEmitter;
  }

  public Observable<List<Uri>> pickMultiPhotosFromAblum() {
    isSingleMode = false;
    photoEmitter = PublishSubject.create();
    startGalleryActivity();
    return photoEmitter;
  }

  private void startGalleryActivity() {
    Intent intent = new Intent(appContext, GalleryActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra(EXTRA_TOOLBAR_COLOR, toolbarColor);
    intent.putExtra(EXTRA_TOOLBAR_TITLE_COLOR, toolbarTitleTextColor);
    intent.putExtra(EXTRA_STATUS_BAR_COLOR, statusBarColor);
    intent.putExtra(EXTRA_SELECTED_BORDER_COLOR, selectedBorderColor);
    intent.putExtra(EXTRA_ACTION_TEXT, actionText);
    intent.putExtra(EXTRA_SELECTED_ICON, selectedIcon);
    intent.putExtra(EXTRA_COL_COUNT, columnCount);
    intent.putExtra(EXTRA_IS_SINGLE_MODE, isSingleMode);
    intent.putExtra(EXTRA_LIMIT, limit);
    appContext.startActivity(intent);
  }

  public Observable<Uri> takePhotoFromCamera() {
    isSingleMode = false;
    photoEmitter = PublishSubject.create();
    Intent intent = new Intent(appContext, CameraHiddenActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    appContext.startActivity(intent);
    return photoEmitter;
  }

  void onPhotoPicked(Uri uri) {
    if (photoEmitter != null) {
      Uri copy = Uri.fromFile(new File(uri.getPath()));
      photoEmitter.onNext(copy);
      photoEmitter.onCompleted();
    }
  }

  void onPhotosPicked(List<Uri> uris) {
    if (photoEmitter != null) {
      List<Uri> copy = new ArrayList<>(uris);
      photoEmitter.onNext(copy);
      photoEmitter.onCompleted();
    }
  }

  void onError(Throwable throwable) {
    if (photoEmitter != null) {
      Throwable copy = new Throwable(throwable);
      photoEmitter.onError(copy);
    }
  }

  @Deprecated
  public Observable<Uri> pickSinglePhoto() {
    isSingleMode = true;
    photoEmitter = PublishSubject.create();
    startGalleryActivity();
    return photoEmitter;
  }

  @Deprecated
  public Observable<List<Uri>> pickMultiPhotos() {
    isSingleMode = false;
    photoEmitter = PublishSubject.create();
    startGalleryActivity();
    return photoEmitter;
  }

}

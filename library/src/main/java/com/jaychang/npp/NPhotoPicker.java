package com.jaychang.npp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.List;

public class NPhotoPicker {

  static final String EXTRA_SELECTED_PHOTOS = "EXTRA_SELECTED_PHOTOS";
  static final String EXTRA_TOOLBAR_COLOR = "EXTRA_TOOLBAR_COLOR";
  static final String EXTRA_STATUS_BAR_COLOR = "EXTRA_STATUS_BAR_COLOR";
  static final String EXTRA_SELECTED_BORDER_COLOR = "EXTRA_SELECTED_BORDER_COLOR";
  static final String EXTRA_SELECTED_ICON = "EXTRA_SELECTED_ICON";
  static final String EXTRA_ACTION_TEXT = "EXTRA_ACTION_TEXT";
  static final String EXTRA_LIMIT = "EXTRA_LIMIT";
  static final String EXTRA_COL_COUNT = "EXTRA_COL_COUNT";
  static final String EXTRA_IS_SINGLE_MODE = "EXTRA_IS_SINGLE_MODE";

  static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1001;

  private int toolbarColor;
  private int statusBarColor;
  private int selectedBorderColor;
  private int selectedIcon;
  private int actionText;
  private int limit;
  private int columnCount;
  private boolean isSingleMode;

  private NPhotoPicker() {
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

  public static NPhotoPicker create() {
    return new NPhotoPicker();
  }

  public NPhotoPicker toolbarColor(@ColorRes int toolbarColor) {
    this.toolbarColor = toolbarColor;
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

  public NPhotoPicker singleMode() {
    this.isSingleMode = true;
    return this;
  }

  public NPhotoPicker multiMode() {
    this.isSingleMode = false;
    return this;
  }

  public void pick(Activity activity, int requestCode) {
    Intent intent = new Intent(activity, GalleryActivity.class);
    intent.putExtra(EXTRA_TOOLBAR_COLOR, toolbarColor);
    intent.putExtra(EXTRA_STATUS_BAR_COLOR, statusBarColor);
    intent.putExtra(EXTRA_SELECTED_BORDER_COLOR, selectedBorderColor);
    intent.putExtra(EXTRA_ACTION_TEXT, actionText);
    intent.putExtra(EXTRA_SELECTED_ICON, selectedIcon);
    intent.putExtra(EXTRA_COL_COUNT, columnCount);
    intent.putExtra(EXTRA_IS_SINGLE_MODE, isSingleMode);
    intent.putExtra(EXTRA_LIMIT, limit);
    activity.startActivityForResult(intent, requestCode);
  }

  public void pick(Fragment fragment, int requestCode) {
    pick(fragment.getActivity(), requestCode);
  }

  public static List<Photo> getPickedPhotos(Intent data) {
    Parcelable[] parcelables = data.getParcelableArrayExtra(NPhotoPicker.EXTRA_SELECTED_PHOTOS);
    Photo[] photos = new Photo[parcelables.length];
    System.arraycopy(parcelables, 0, photos, 0, parcelables.length);
    return Arrays.asList(photos);
  }
}

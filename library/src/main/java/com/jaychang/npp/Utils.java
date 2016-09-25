package com.jaychang.npp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

final class Utils {

  public static int dpToPx(Context context, int dp) {
    float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
    return (int) (dp * density);
  }

  public static int getScreenWidthPixels(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  public static int getPrimaryColor(Context context) {
    TypedValue typedValue = new TypedValue();

    TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
    int color = a.getColor(0, 0);

    a.recycle();

    return color;
  }

}

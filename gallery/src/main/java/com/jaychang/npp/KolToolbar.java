package com.jaychang.npp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class KolToolbar extends LinearLayout {

  private ImageView leftIconView;
  private TextView leftTextView;
  private ImageView titleIconView;
  private TextView titleTextView;
  private TextView subtitleTextView;
  private ImageView rightIconView;
  private ImageView rightIcon2View;
  private TextView rightTextView;
  private int leftIcon;
  private String leftText;
  private float leftTextSize;
  private int leftTextIcon;
  private int leftTextIconPosition;
  private ColorStateList leftTextColor;
  private int leftTextStyle;
  private int titleIcon;
  private String title;
  private ColorStateList titleTextColor;
  private float titleTextSize;
  private int titleTextStyle;
  private String subtitle;
  private ColorStateList subtitleTextColor;
  private float subtitleTextSize;
  private int subtitleTextStyle;
  private int rightIcon;
  private int rightIcon2;
  private String rightText;
  private float rightTextSize;
  private int rightTextIcon;
  private int rightTextIconPosition;
  private ColorStateList rightTextColor;
  private int rightTextStyle;

  public KolToolbar(Context context) {
    this(context, null);
  }

  public KolToolbar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public KolToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflateViews(context);
    initAttrs(context, attrs, defStyleAttr);
    initViews();
  }

  private void inflateViews(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.view_kol_toolbar, this);
    leftIconView = (ImageView) view.findViewById(R.id.left_icon);
    leftTextView = (TextView) view.findViewById(R.id.left_text);
    titleIconView = (ImageView) view.findViewById(R.id.title_icon);
    titleTextView = (TextView) view.findViewById(R.id.title);
    subtitleTextView = (TextView) view.findViewById(R.id.subtitle);
    rightIconView = (ImageView) view.findViewById(R.id.right_icon);
    rightIcon2View = (ImageView) view.findViewById(R.id.right_icon_2);
    rightTextView = (TextView) view.findViewById(R.id.right_text);
  }

  private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray typedArray = context.getTheme()
      .obtainStyledAttributes(attrs, R.styleable.KolToolbar, defStyleAttr, 0);

    leftIcon = typedArray.getResourceId(R.styleable.KolToolbar_npp_leftIcon, 0);
    leftText = typedArray.getString(R.styleable.KolToolbar_npp_leftText);
    leftTextSize = typedArray.getDimensionPixelSize(R.styleable.KolToolbar_npp_leftTextSize, 0);
    leftTextIcon = typedArray.getResourceId(R.styleable.KolToolbar_npp_leftTextIcon, 0);
    leftTextIconPosition = typedArray.getInt(R.styleable.KolToolbar_npp_leftTextIconPosition, 0);
    leftTextColor = typedArray.getColorStateList(R.styleable.KolToolbar_npp_leftTextColor);
    leftTextStyle = typedArray.getInt(R.styleable.KolToolbar_npp_leftTextStyle, 0);

    titleIcon = typedArray.getResourceId(R.styleable.KolToolbar_npp_titleIcon, 0);
    title = typedArray.getString(R.styleable.KolToolbar_npp_title);
    titleTextColor = typedArray.getColorStateList(R.styleable.KolToolbar_npp_titleTextColor);
    titleTextSize = typedArray.getDimensionPixelSize(R.styleable.KolToolbar_npp_titleTextSize, 0);
    titleTextStyle = typedArray.getInt(R.styleable.KolToolbar_npp_titleTextStyle, 0);

    subtitle = typedArray.getString(R.styleable.KolToolbar_npp_subtitle);
    subtitleTextColor = typedArray.getColorStateList(R.styleable.KolToolbar_npp_subtitleTextColor);
    subtitleTextSize = typedArray.getDimensionPixelSize(R.styleable.KolToolbar_npp_subtitleTextSize, 0);
    subtitleTextStyle = typedArray.getInt(R.styleable.KolToolbar_npp_subtitleTextStyle, 0);

    rightIcon = typedArray.getResourceId(R.styleable.KolToolbar_npp_rightIcon, 0);
    rightIcon2 = typedArray.getResourceId(R.styleable.KolToolbar_npp_rightIcon2, 0);
    rightText = typedArray.getString(R.styleable.KolToolbar_npp_rightText);
    rightTextSize = typedArray.getDimensionPixelSize(R.styleable.KolToolbar_npp_rightTextSize, 0);
    rightTextIcon = typedArray.getResourceId(R.styleable.KolToolbar_npp_rightTextIcon, 0);
    rightTextIconPosition = typedArray.getInt(R.styleable.KolToolbar_npp_rightTextIconPosition, 0);
    rightTextColor = typedArray.getColorStateList(R.styleable.KolToolbar_npp_rightTextColor);
    rightTextStyle = typedArray.getInt(R.styleable.KolToolbar_npp_rightTextStyle, 0);

    typedArray.recycle();
  }

  private void initViews() {
    if (leftIcon != 0) {
      setLeftIcon(leftIcon);
    }
    if (leftTextIcon != 0) {
      setLeftTextIcon(leftTextIcon);
    }
    if (leftTextColor != null) {
      setLeftTextColor(leftTextColor);
    }
    if (leftTextStyle != 0) {
      setLeftTextStyle(leftTextStyle);
    }
    if (!TextUtils.isEmpty(leftText)) {
      setLeftText(leftText);
    }
    if (leftTextSize != 0) {
      setLeftTextSize(leftTextSize);
    }

    if (titleIcon != 0) {
      setTitleIcon(titleIcon);
    }
    if (titleTextColor != null) {
      setTitleTextColor(titleTextColor);
    }
    if (titleTextSize != 0) {
      setTitleTextSize(titleTextSize);
    }
    if (titleTextStyle != 0) {
      setTitleTextTypeface(titleTextStyle);
    }
    if (!TextUtils.isEmpty(title)) {
      setTitle(title);
    }

    if (subtitleTextColor != null) {
      setSubtitleTextColor(subtitleTextColor);
    }
    if (subtitleTextSize != 0) {
      setSubtitleTextSize(subtitleTextSize);
    }
    if (subtitleTextStyle != 0) {
      setSubtitleTextStyle(subtitleTextStyle);
    }
    if (!TextUtils.isEmpty(subtitle)) {
      setSubtitle(subtitle);
    }

    if (rightIcon != 0) {
      setRightIcon(rightIcon);
    }
    if (rightIcon2 != 0) {
      setRightIcon2(rightIcon2);
    }
    if (rightTextIcon != 0) {
      setRightTextIcon(rightTextIcon);
    }
    if (rightTextColor != null) {
      setRightTextColor(rightTextColor);
    }
    if (rightTextStyle != 0) {
      setRightTextStyle(rightTextStyle);
    }
    if (!TextUtils.isEmpty(rightText)) {
      setRightText(rightText);
    }
    if (rightTextSize != 0) {
      setRightTextSize(rightTextSize);
    }
  }

  /**
   * title
   */
  public void setTitle(int stringRes) {
    setTitle(getResources().getString(stringRes));
  }

  public void setTitle(CharSequence text) {
    titleTextView.setText(text);
    titleTextView.setVisibility(View.VISIBLE);
    titleIconView.setVisibility(View.GONE);
  }

  public void setTitleIcon(int drawableRes) {
    titleIconView.setImageResource(drawableRes);
    titleTextView.setVisibility(View.GONE);
    titleIconView.setVisibility(View.VISIBLE);
  }

  public void setTitleTextColor(@ColorInt int color) {
    titleTextView.setTextColor(color);
  }

  public void setTitleTextColor(ColorStateList colorStateList) {
    titleTextView.setTextColor(colorStateList);
  }

  public void setTitleTextTypeface(int typeface) {
    titleTextView.setTypeface(null, typeface);
  }

  public void setTitleTextSize(float textSize) {
    titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
  }

  /**
   * subtitle
   */
  public void setSubtitle(int stringRes) {
    setSubtitle(getResources().getString(stringRes));
  }

  public void setSubtitle(CharSequence text) {
    subtitleTextView.setText(text);
    subtitleTextView.setVisibility(View.VISIBLE);
  }

  public void setSubtitleTextColor(@ColorInt int color) {
    subtitleTextView.setTextColor(color);
  }

  public void setSubtitleTextColor(ColorStateList colorStateList) {
    subtitleTextView.setTextColor(colorStateList);
  }

  public void setSubtitleTextStyle(int style) {
    subtitleTextView.setTypeface(null, style);
  }

  public void setSubtitleTextSize(float textSize) {
    subtitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
  }

  /**
   * left
   */
  public void setLeftText(int stringRes) {
    setLeftText(getResources().getString(stringRes));
  }

  public void setLeftText(CharSequence text) {
    leftTextView.setText(text);
    leftTextView.setVisibility(View.VISIBLE);
    leftIconView.setVisibility(View.GONE);
  }

  public void setLeftTextIcon(int leftTextIcon) {
    if (leftTextIconPosition == 0) {
      leftTextView.setCompoundDrawablesWithIntrinsicBounds(leftTextIcon, 0, 0, 0);
    } else {
      leftTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, leftTextIcon, 0);
    }
  }

  public void setLeftTextColor(@ColorInt int color) {
    leftTextView.setTextColor(color);
  }

  public void setLeftTextColor(ColorStateList colorStateList) {
    leftTextView.setTextColor(colorStateList);
  }

  public void setLeftTextStyle(int style) {
    leftTextView.setTypeface(null, style);
  }

  public void setLeftIcon(int drawableRes) {
    leftIconView.setImageResource(drawableRes);
    leftTextView.setVisibility(View.GONE);
    leftIconView.setVisibility(View.VISIBLE);
  }

  public void setLeftTextSize(float textSize) {
    leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
  }

  public void setLeftTextIcon(int leftTextIcon, boolean isLeft) {
    this.leftTextIconPosition = isLeft ? 0 : 1;
    setLeftTextIcon(leftTextIcon);
  }

  /**
   * right
   */
  public void setRightText(int stringRes) {
    setRightText(getResources().getString(stringRes));
  }

  public void setRightText(CharSequence text) {
    rightTextView.setText(text);
    rightTextView.setVisibility(View.VISIBLE);
    rightIconView.setVisibility(View.GONE);
  }

  public void setRightTextIcon(int rightTextIcon) {
    if (rightTextIconPosition == 0) {
      rightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightTextIcon, 0);
    } else {
      rightTextView.setCompoundDrawablesWithIntrinsicBounds(rightTextIcon, 0, 0, 0);
    }
  }

  public void setRightTextIcon(int rightTextIcon, boolean isRight) {
    this.rightTextIconPosition = isRight ? 0 : 1;
    setRightTextIcon(rightTextIcon);
  }

  public void setRightTextColor(@ColorInt int color) {
    rightTextView.setTextColor(color);
  }

  public void setRightTextColor(ColorStateList colorStateList) {
    rightTextView.setTextColor(colorStateList);
  }

  public void setRightTextStyle(int style) {
    rightTextView.setTypeface(null, style);
  }

  public void setRightIcon(int drawableRes) {
    rightIconView.setImageResource(drawableRes);
    rightTextView.setVisibility(View.GONE);
    rightIconView.setVisibility(View.VISIBLE);
  }

  public void setRightIcon2(int drawableRes) {
    rightIcon2View.setImageResource(drawableRes);
    rightTextView.setVisibility(View.GONE);
    rightIcon2View.setVisibility(View.VISIBLE);
  }

  public void setRightTextSize(float textSize) {
    rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
  }

  /**
   * getters
   */
  public ImageView getLeftIconView() {
    return leftIconView;
  }

  public TextView getLeftTextView() {
    return leftTextView;
  }

  public ImageView getTitleIconView() {
    return titleIconView;
  }

  public TextView getTitleTextView() {
    return titleTextView;
  }

  public TextView getSubtitleTextView() {
    return subtitleTextView;
  }

  public ImageView getRightIconView() {
    return rightIconView;
  }

  public ImageView getRightIcon2View() {
    return rightIcon2View;
  }

  public TextView getRightTextView() {
    return rightTextView;
  }

}

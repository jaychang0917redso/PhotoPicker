package com.jaychang.npp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Locale;

import static com.jaychang.utils.AppUtils.getLauncherIntent;

public class LangUtils {

  private static final String KEY_CURRENT_LANG = "KEY_CURRENT_LANG";
  private static final String KEY_CURRENT_COUNTRY = "KEY_CURRENT_COUNTRY";

  private static Locale defaultLocale = null;

  public static void changeLanguage(Context context, Locale locale) {
    saveString(context, getKeyCurrentLang(context), locale.getLanguage());
    saveString(context, getKeyCurrentCountry(context), locale.getCountry());

    wrap(context);

    Intent refresh = getLauncherIntent(context);
    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(refresh);
  }

  public static Context wrap(Context context) {
    Context ctx = context;
    Locale locale = getLocale(context);
    Locale.setDefault(locale);

    Resources res = ctx.getResources();
    Configuration config = new Configuration(res.getConfiguration());
    if (Build.VERSION.SDK_INT >= 17) {
      config.setLocale(locale);
      ctx = context.createConfigurationContext(config);
    } else {
      config.locale = locale;
      res.updateConfiguration(config, res.getDisplayMetrics());
    }
    return ctx;
  }

  public static Locale getLocale(Context context) {
    String lang = getString(context, getKeyCurrentLang(context));
    String country = getString(context, getKeyCurrentCountry(context));
    if (!TextUtils.isEmpty(lang)) {
      Locale locale = new Locale(lang, country);
      if (locale.getLanguage().equalsIgnoreCase(Locale.ENGLISH.getLanguage())) {
        return Locale.ENGLISH;
      } else {
        return locale;
      }
    } else {
      Locale locale;
      if (Locale.getDefault().getLanguage().equalsIgnoreCase(Locale.ENGLISH.getLanguage())) {
        locale = Locale.ENGLISH;
      } else {
        locale = new Locale(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
      }

      return defaultLocale != null ? defaultLocale : locale;
    }
  }

  public static void restore(Context context, Locale defaultLocale) {
    LangUtils.defaultLocale = defaultLocale;
    Locale.setDefault(getLocale(context));
  }

  private static String getKeyCurrentLang(Context context) {
    return context.getPackageName() + KEY_CURRENT_LANG;
  }

  private static String getKeyCurrentCountry(Context context) {
    return context.getPackageName() + KEY_CURRENT_COUNTRY;
  }

  @SuppressLint("ApplySharedPref")
  private static void saveString(Context context, String key, String value) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putString(key, value).commit();
  }

  private static String getString(Context context, String key) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getString(key, "");
  }

}
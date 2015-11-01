package com.sistemasoperativos.denny.rssreader.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.view.Display;

import com.sistemasoperativos.denny.rssreader.R;

/**
 * Created by denny on 07/07/15.
 */
public class Utils {

  public static Point getDisplaySize(Activity activity) {
    Display display = activity.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  public static boolean isPortrait(Activity activity) {
    return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
  }

  public static boolean isLandscape(Activity activity) {
    return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
  }

  /**
   *
   * @return
   */
  public static int readFromSharedPreferences(Activity activity, int string) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
    int time = sharedPref.getInt(activity.getResources().getString(string), 1);
    return time;
  }

  /**
   *
   * @param value
   */
  public static void saveToSharedPreferences(Activity activity, int string, int value) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putInt(activity.getResources().getString(string), value);
    editor.commit();
  }

}

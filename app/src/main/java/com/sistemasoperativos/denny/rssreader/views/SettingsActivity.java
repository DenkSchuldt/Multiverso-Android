package com.sistemasoperativos.denny.rssreader.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sistemasoperativos.denny.rssreader.utils.Utils;
import com.sistemasoperativos.denny.rssreader.views.dialogfragments.DeleteNewsDialogFragment;
import com.sistemasoperativos.denny.rssreader.interfaces.OnSettingsEvent;
import com.sistemasoperativos.denny.rssreader.R;
import com.sistemasoperativos.denny.rssreader.views.dialogfragments.FetchContentDialogFragment;

/**
 * Created by denny on 27/06/15.
 */
public class SettingsActivity extends AppCompatActivity implements OnSettingsEvent {

  public static final String SETTINGS_FETCH_CONTENT_TIME = "settingsFetchContentTime";
  public static final String SETTINGS_DELETE_NEWS_TIME = "settingsDeleteNewsTime";

  private int deleteNewsTime;
  private int fetchContentTime;
  private ViewHolder viewHolder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    viewHolder = new ViewHolder();
    viewHolder.buildLayout();
    setCustomActionBar();
  }

  @Override
  protected void onStart() {
    super.onStart();
    updateContent();
  }

  @Override
  public void onSettingsFetchContentTime() {
    updateFetchContentTime();
  }

  @Override
  public void onSettingsDeleteNewsTime() {
    updateDeleteNewsTime();
  }

  /**
   *
   */
  public void setCustomActionBar() {
    viewHolder.toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(viewHolder.toolbar);
    getSupportActionBar().setTitle(getResources().getString(R.string.actionbar_title_settings));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setElevation(8);
  }

  public void updateContent() {
    updateFetchContentTime();
    updateDeleteNewsTime();
    updateVersionName();
  }

  /**
   *
   */
  public void updateFetchContentTime() {
    fetchContentTime = Utils.readFromSharedPreferences(
        SettingsActivity.this,
        R.string.shared_preferences_settings_fetch_content_time);
    if (fetchContentTime == 1) {
      viewHolder.fetchContentTimeLabel.setText("Cada " + fetchContentTime + " "
          + getResources().getString(R.string.settings_fetch_content_time_unit_singular));
    } else {
      viewHolder.fetchContentTimeLabel.setText("Cada " + fetchContentTime + " "
          + getResources().getString(R.string.settings_fetch_content_time_unit_plural));
    }
  }

  /**
   *
   */
  public void updateDeleteNewsTime() {
    deleteNewsTime = Utils.readFromSharedPreferences(
        SettingsActivity.this,
        R.string.shared_preferences_settings_delete_news_time);
    if (deleteNewsTime == 1) {
      viewHolder.deleteNewsTimeLabel.setText("Cada " + deleteNewsTime + " "
          + getResources().getString(R.string.settings_delete_news_time_unit_singular));
    } else {
      viewHolder.deleteNewsTimeLabel.setText("Cada " + deleteNewsTime + " "
          + getResources().getString(R.string.settings_delete_news_time_unit_plural));
    }
  }

  /**
   *
   */
  public void updateVersionName() {
    try {
      PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      viewHolder.versionLabel.setText("v." + pInfo.versionName);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }


  /**
  *
  */
  private class ViewHolder implements View.OnClickListener{

    private Toolbar toolbar;

    private TextView fetchContentTimeLabel;
    private TextView deleteNewsTimeLabel;
    private TextView versionLabel;

    private LinearLayout fetchContentTime;
    private LinearLayout deleteNewsTime;
    private LinearLayout versionName;
    private LinearLayout sendComments;

    public void buildLayout() {
      findViews();
      setOnClickListeners();
    }

    /**
     *
     */
    private void findViews() {
      toolbar = (Toolbar) findViewById(R.id.toolbar);

      fetchContentTimeLabel = (TextView) findViewById(R.id.settings_fetch_content_time_label);
      deleteNewsTimeLabel = (TextView) findViewById(R.id.settings_delete_news_time_label);
      versionLabel = (TextView) findViewById(R.id.settings_version_label);

      fetchContentTime = (LinearLayout) findViewById(R.id.settings_fetch_content_time);
      deleteNewsTime = (LinearLayout) findViewById(R.id.settings_delete_news_time);
      versionName = (LinearLayout) findViewById(R.id.settings_version_name);
      sendComments = (LinearLayout) findViewById(R.id.settings_send_comments);
    }

    /**
     *
     */
    private void setOnClickListeners() {
      fetchContentTime.setOnClickListener(this);
      deleteNewsTime.setOnClickListener(this);
      versionName.setOnClickListener(this);
      sendComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.settings_fetch_content_time:
          FetchContentDialogFragment fcdf = FetchContentDialogFragment.newInstance(
              SettingsActivity.this.fetchContentTime);
          fcdf.show(getSupportFragmentManager(), FetchContentDialogFragment.TAG);
          break;
        case R.id.settings_delete_news_time:
          DeleteNewsDialogFragment dndf = DeleteNewsDialogFragment.newInstance(
              SettingsActivity.this.deleteNewsTime);
          dndf.show(getSupportFragmentManager(), FetchContentDialogFragment.TAG);
          break;
        case R.id.settings_send_comments:
          Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "dschuldtv@gmail.com", null));
          i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Comentarios sobre MULTIVERSO");
          startActivity(Intent.createChooser(i, getResources().getString(R.string.settings_comments_label)));
          break;
      }
    }

  }

}

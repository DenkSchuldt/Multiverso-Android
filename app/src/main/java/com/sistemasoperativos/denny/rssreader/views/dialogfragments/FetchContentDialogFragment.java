package com.sistemasoperativos.denny.rssreader.views.dialogfragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.sistemasoperativos.denny.rssreader.interfaces.OnSettingsEvent;
import com.sistemasoperativos.denny.rssreader.R;
import com.sistemasoperativos.denny.rssreader.utils.Utils;
import com.sistemasoperativos.denny.rssreader.views.SettingsActivity;

/**
 * Created by denny on 27/06/15.
 */
public class FetchContentDialogFragment extends DialogFragment {

  public static final String TAG = "FetchContentDialogFragment";

  private ViewHolder viewHolder;
  private OnSettingsEvent onSettingsEvent;
  private int fetchContentTime;

  public static FetchContentDialogFragment newInstance(int value) {
    FetchContentDialogFragment fdf = new FetchContentDialogFragment();
    Bundle args = new Bundle();
    args.putInt(SettingsActivity.SETTINGS_FETCH_CONTENT_TIME, value);
    fdf.setArguments(args);
    return fdf;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    onSettingsEvent = (OnSettingsEvent) activity;
    Log.i(FetchContentDialogFragment.class.getSimpleName(), "Fragment attached to activity.");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    fetchContentTime = getFetchContentTimeFromArguments();
    viewHolder = new ViewHolder(inflater.inflate(R.layout.fragment_number_picker, container,false));
    viewHolder.buildLayout();
    return viewHolder.root;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupDialog();
    viewHolder.numberPicker.setMaxValue(100);
    viewHolder.numberPicker.setMinValue(1);
    viewHolder.numberPicker.setValue(fetchContentTime);
    viewHolder.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override
      public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (newVal == 1) {
          viewHolder.units.setText(getResources().getString(R.string.settings_fetch_content_time_unit_singular));
        } else {
          viewHolder.units.setText(getResources().getString(R.string.settings_fetch_content_time_unit_plural));
        }
      }
    });
    if (fetchContentTime == 1) {
      viewHolder.units.setText(getResources().getString(R.string.settings_fetch_content_time_unit_singular));
    } else {
      viewHolder.units.setText(getResources().getString(R.string.settings_fetch_content_time_unit_plural));
    }
  }

  /**
   *
   */
  public void setupDialog() {
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    setCancelable(false);
    viewHolder.title.setText(getResources().getString(R.string.settings_fetch_content_time_title));
  }

  /**
   *
   * @return
   */
  public int getFetchContentTimeFromArguments() {
    return getArguments().getInt(SettingsActivity.SETTINGS_FETCH_CONTENT_TIME);
  }

  /**
   *
   */
  public class ViewHolder implements View.OnClickListener {

    public View root;
    public TextView title;
    public TextView units;
    public NumberPicker numberPicker;
    public Button cancel;
    public Button accept;

    public ViewHolder(View view) {
      root = view;
    }

    public void buildLayout() {
      findViews();
      setOnClickListeners();
    }

    private void findViews() {
      title = (TextView) root.findViewById(R.id.settings_dialog_title);
      units = (TextView) root.findViewById(R.id.units);
      numberPicker = (NumberPicker) root.findViewById(R.id.settings_dialog_number_picker);
      cancel = (Button) root.findViewById(R.id.settings_dialog_cancel);
      accept = (Button) root.findViewById(R.id.settings_dialog_accept);
    }

    private void setOnClickListeners() {
      cancel.setOnClickListener(this);
      accept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.settings_dialog_cancel:
          dismiss();
          break;
        case R.id.settings_dialog_accept:
          Utils.saveToSharedPreferences(
              getActivity(),
              R.string.shared_preferences_settings_fetch_content_time,
              numberPicker.getValue());
          onSettingsEvent.onSettingsFetchContentTime();
          dismiss();
          break;
      }
    }
  }

}

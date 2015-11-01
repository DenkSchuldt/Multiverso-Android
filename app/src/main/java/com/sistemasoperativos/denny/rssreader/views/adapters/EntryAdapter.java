package com.sistemasoperativos.denny.rssreader.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sistemasoperativos.denny.rssreader.R;
import com.sistemasoperativos.denny.rssreader.models.Entry;
import com.sistemasoperativos.denny.rssreader.views.EntryDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by denny on 31/10/15.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

  private Activity activity;
  private ArrayList<Entry> entries;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ViewHolder(View view) {
      super(view);
      this.view = view;
    }
  }

  public EntryAdapter(Activity activity, ArrayList<Entry> entries) {
    this.activity = activity;
    this.entries = entries;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(activity).inflate(R.layout.entry, parent, false);
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        view.setOnClickListener(new EntryClickListener());
      }
    });
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Entry entry = entries.get(position);
    holder.view.setTag(entry);

    TextView title = (TextView) holder.view.findViewById(R.id.entry_title);
    TextView category = (TextView) holder.view.findViewById(R.id.entry_category);
    TextView time = (TextView) holder.view.findViewById(R.id.entry_time);
    ImageView media = (ImageView) holder.view.findViewById(R.id.entry_media);

    title.setText(entry.getTitle());
    category.setText(entry.getCategory());

    String pubDate = entry.getPubDate();
    String[] date = pubDate.split("\\s+");

    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int pubDay = Integer.parseInt(date[1]);

    if (pubDay == day)
      time.setText("Hoy, " + date[4].substring(0,5));
    else if (pubDay == day-1)
      time.setText("Ayer, " + date[4].substring(0,5));
    else
      time.setText(date[2] + " " + date[1] + ", " + date[4].substring(0,5));

    if (!entry.getImgurl().isEmpty()) {
      Picasso.with(activity).load(entry.getImgurl()).into(media);
    } else {
      media.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return entries.size();
  }


  public ArrayList<Entry> getEntries() { return entries; }
  public void setEntries(ArrayList<Entry> entries) { this.entries = entries; }

  /**
   *
   */
  private class EntryClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      Entry entry = (Entry) v.getTag();
      EntryDialogFragment edf = EntryDialogFragment.newInstance(entry);
      edf.show(activity.getFragmentManager(), EntryDialogFragment.TAG);
    }
  }

}

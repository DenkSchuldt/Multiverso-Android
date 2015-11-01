package com.sistemasoperativos.denny.rssreader.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sistemasoperativos.denny.rssreader.R;
import com.sistemasoperativos.denny.rssreader.interfaces.OnEntryEvent;
import com.sistemasoperativos.denny.rssreader.models.Entry;
import com.sistemasoperativos.denny.rssreader.utils.Constants;
import com.sistemasoperativos.denny.rssreader.views.adapters.EntryAdapter;

import java.util.ArrayList;

/**
 * Created by denny on 14/06/15.
 */
public class EntriesFragment extends Fragment {

  private String TAG;
  private ViewHolder viewHolder;
  private OnEntryEvent onEntryEvent;
  private EntryAdapter entryAdapter;

  public static EntriesFragment newInstance(String TAG) {
    EntriesFragment fragment = new EntriesFragment();
    Bundle args = new Bundle();
    args.putString(Constants.TAG, TAG);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      onEntryEvent = (OnEntryEvent) getActivity();
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()+ " must implement OnEntryEvent");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    TAG = getArguments().getString(Constants.TAG);
    entryAdapter = new EntryAdapter(getActivity(), new ArrayList<Entry>());
    viewHolder = new ViewHolder(inflater.inflate(R.layout.fragment_entries,container,false));
    viewHolder.buildLayout();
    return viewHolder.root;
  }

  @Override
  public void onResume() {
    super.onResume();
    onEntryEvent.updateContent(TAG);
  }

  /**
   *
   * @param entry
   */
  public void updateContent(final Entry entry) {
    if(!entryExits(entry)) {
      entryAdapter.getEntries().add(entry);
      viewHolder.empty.setVisibility(View.GONE);
      entryAdapter.notifyDataSetChanged();
    }
  }

  /**
   *
   * @param entry
   * @return
   */
  public boolean entryExits(Entry entry) {
    for (Entry e : entryAdapter.getEntries()) {
      if (e.getTitle().equals(entry.getTitle()))
        return true;
    }
    return false;
  }

  /**
   *
   */
  public class ViewHolder {

    public View root;
    public RecyclerView entries;
    public LinearLayout empty;

    public ViewHolder(View view) {
      root = view;
    }

    public void buildLayout() {
      findViews();
    }

    /**
     *
     */
    private void findViews() {
      entries = (RecyclerView) root.findViewById(R.id.entries);
      entries.setHasFixedSize(false);
      entries.setLayoutManager(
          new StaggeredGridLayoutManager(
              getResources().getInteger(R.integer.recycler_view_columns),
              StaggeredGridLayoutManager.VERTICAL));
      entries.setAdapter(entryAdapter);
      empty = (LinearLayout) root.findViewById(R.id.empty);
    }

  }

}

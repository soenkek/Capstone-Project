package com.dev.soenkek.redditviewer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.SearchableAdapterViewHolder> {

    private ArrayList<Subreddit> mData;
    private RecyclerView mRecyclerView;
    int mExpandedPosition = -1;
    int mPreviousExpandedPosition = -1;

    public SearchableAdapter(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @NonNull
    @Override
    public SearchableAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SearchableAdapterViewHolder(inflater.inflate(R.layout.item_searchable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchableAdapterViewHolder holder, final int position) {
        String iconUrl = mData.get(position).getIconUrl();
        String name = mData.get(position).getName();
//        TODO format number
        String numSubscribers = String.valueOf(mData.get(position).getNumSubscribers()) + " subscribers";
        String description = mData.get(position).getDescription();

        holder.mNameTv.setText(name);
        holder.mNumSubscribersTv.setText(numSubscribers);
        holder.mDescriptionTv.setText(description);
        if (iconUrl != null && !iconUrl.equals("")) {
            Picasso.get().load(iconUrl).into(holder.mIconIv);
        }
        final boolean isExpanded = (position == mExpandedPosition);
        holder.mDescriptionTv.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded) mPreviousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(mRecyclerView);
//                notifyDataSetChanged();
                notifyItemChanged(position);
                notifyItemChanged(mPreviousExpandedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public class SearchableAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIconIv;
        public final TextView mNameTv;
        public final TextView mNumSubscribersTv;
        public final TextView mDescriptionTv;

        public SearchableAdapterViewHolder(View itemView) {
            super(itemView);
            mIconIv = itemView.findViewById(R.id.item_searchable_icon_iv);
            mNameTv = itemView.findViewById(R.id.item_searchable_name_tv);
            mNumSubscribersTv = itemView.findViewById(R.id.item_searchable_num_subscribers_tv);
            mDescriptionTv = itemView.findViewById(R.id.item_searchable_description_tv);
        }
    }

    public void addData(Subreddit[] subreddits) {
        if (subreddits != null) {
            for (Subreddit subreddit : subreddits) {
                if (mData == null) mData = new ArrayList<>();
                mData.add(subreddit);
                notifyDataSetChanged();
            }
        }
    }
}

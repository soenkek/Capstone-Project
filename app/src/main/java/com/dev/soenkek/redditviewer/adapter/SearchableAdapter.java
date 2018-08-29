package com.dev.soenkek.redditviewer.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.soenkek.redditviewer.R;
import com.dev.soenkek.redditviewer.data.DbContract;
import com.dev.soenkek.redditviewer.models.Subreddit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.SearchableAdapterViewHolder> {

    private SubscribeClickListener mSubscribeListener;
    private ArrayList<Subreddit> mData;
    private RecyclerView mRecyclerView;
    int mExpandedPosition = -1;
    int mPreviousExpandedPosition = -1;
    private Context mContext;

    public interface SubscribeClickListener {
        void onSubscribe(Subreddit subreddit);
        void onUnsubscribe(Subreddit subreddit);
    }

    public SearchableAdapter(SubscribeClickListener mSubscribeListener, RecyclerView mRecyclerView, Context mContext) {
        this.mSubscribeListener = mSubscribeListener;
        this.mRecyclerView = mRecyclerView;
        this.mContext = mContext;
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
        holder.mSubscribeBtn.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (holder.mSubscribeBtn.getVisibility() == View.VISIBLE) {
            initializeSubscribeBtn(holder, position);
        }

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

    private void initializeSubscribeBtn(SearchableAdapterViewHolder holder, final int position) {
        String name = mData.get(position).getName();
        Uri uri = DbContract.Subscriptions.CONTENT_URI.buildUpon().appendPath(name).build();
        //        TODO db operations in background thread
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            holder.mSubscribeBtn.setText(R.string.label_unsubscribe);
        } else {
            holder.mSubscribeBtn.setText(R.string.label_subscribe);
        }
        holder.mSubscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = ((Button) v).getText().toString();
                if (btnText.equals(mContext.getResources().getString(R.string.label_subscribe))) {
                    mSubscribeListener.onSubscribe(mData.get(position));
                    Uri uri = DbContract.Subscriptions.CONTENT_URI;
                    ContentValues cv = new ContentValues();
                    cv.put(DbContract.Subscriptions.COLUMN_NAME, mData.get(position).getName());
                    cv.put(DbContract.Subscriptions.COLUMN_ICON_URL, mData.get(position).getIconUrl());
                    mContext.getContentResolver().insert(uri, cv);
                    ((Button) v).setText(R.string.label_unsubscribe);
                } else if (btnText.equals(mContext.getResources().getString(R.string.label_unsubscribe))) {
                    mSubscribeListener.onUnsubscribe(mData.get(position));
                    Uri uri = DbContract.Subscriptions.CONTENT_URI.buildUpon().appendPath(mData.get(position).getName()).build();
                    mContext.getContentResolver().delete(uri, null, null);
                    ((Button) v).setText(R.string.label_subscribe);
                }
            }
        });

        cursor.close();
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
        public final Button mSubscribeBtn;

        public SearchableAdapterViewHolder(View itemView) {
            super(itemView);
            mIconIv = itemView.findViewById(R.id.item_searchable_icon_iv);
            mNameTv = itemView.findViewById(R.id.item_searchable_name_tv);
            mNumSubscribersTv = itemView.findViewById(R.id.item_searchable_num_subscribers_tv);
            mDescriptionTv = itemView.findViewById(R.id.item_searchable_description_tv);
            mSubscribeBtn = itemView.findViewById(R.id.item_searchable_num_subscribe_btn);
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

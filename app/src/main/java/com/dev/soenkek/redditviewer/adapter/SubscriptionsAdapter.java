package com.dev.soenkek.redditviewer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.SubscriptionsAdapterViewHolder> {

    private ArrayList<Subreddit> mData;
    private UnsubscribeListener mCallback;
    private Context mContext;

    public interface UnsubscribeListener {
        void onUnsubscribe(Subreddit subreddit);
        void onSubscribe(Subreddit subreddit);
    }

    public SubscriptionsAdapter(UnsubscribeListener mCallback, Context mContext) {
        this.mCallback = mCallback;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SubscriptionsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SubscriptionsAdapterViewHolder(inflater.inflate(R.layout.item_subscriptions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionsAdapter.SubscriptionsAdapterViewHolder holder, final int position) {
        String iconUrl = mData.get(position).getIconUrl();
        String name = mData.get(position).getName();

        holder.mNameTv.setText(name);
        if (iconUrl != null && !iconUrl.equals("")) {
            Picasso.get().load(iconUrl).into(holder.mIconIv);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void setmData(ArrayList<Subreddit> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public class SubscriptionsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mIconIv;
        public final TextView mNameTv;
        public final Button mSubscribeBtn;

        public SubscriptionsAdapterViewHolder(View itemView) {
            super(itemView);
            mIconIv = itemView.findViewById(R.id.item_subscriptions_icon_iv);
            mNameTv = itemView.findViewById(R.id.item_subscriptions_name_tv);
            mSubscribeBtn = itemView.findViewById(R.id.item_subscriptions_subscribe_btn);
            mSubscribeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Subreddit subreddit = mData.get(getAdapterPosition());
            String btnText = ((Button) v).getText().toString();
            if (btnText.equals(mContext.getResources().getString(R.string.label_subscribe))) {
                mCallback.onSubscribe(subreddit);
                ((Button) v).setText(R.string.label_unsubscribe);
            } else if (btnText.equals(mContext.getResources().getString(R.string.label_unsubscribe))) {
                mCallback.onUnsubscribe(subreddit);
                ((Button) v).setText(R.string.label_subscribe);
            }
        }
    }
}

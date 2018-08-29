package com.dev.soenkek.redditviewer.adapter;

import android.content.Context;
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
import com.dev.soenkek.redditviewer.models.Comment;
import com.dev.soenkek.redditviewer.models.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentsPreviewAdapter extends RecyclerView.Adapter<CommentsPreviewAdapter.CommentsPreviewAdapterViewHolder> {

    private ArrayList<Comment> mData;

    public CommentsPreviewAdapter() {
    }

    @NonNull
    @Override
    public CommentsPreviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CommentsPreviewAdapterViewHolder(inflater.inflate(R.layout.item_comments_preview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsPreviewAdapterViewHolder holder, final int position) {
        String author = mData.get(position).getAuthor();
        String score = String.valueOf(mData.get(position).getScore());
        String time = mData.get(position).getTimestamp();
        String text = mData.get(position).getText();

        holder.mAuthorTv.setText(author);
        holder.mScoreTv.setText(score);
        holder.mTimeTv.setText(time);
        holder.mTextTv.setText(text);
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void setmData(ArrayList<Comment> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public class CommentsPreviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAuthorTv;
        public final TextView mScoreTv;
        public final TextView mTimeTv;
        public final TextView mTextTv;

        public CommentsPreviewAdapterViewHolder(View itemView) {
            super(itemView);
            mAuthorTv = itemView.findViewById(R.id.item_comments_preview_author);
            mScoreTv = itemView.findViewById(R.id.item_comments_preview_score);
            mTimeTv = itemView.findViewById(R.id.item_comments_preview_timestamp);
            mTextTv = itemView.findViewById(R.id.item_comments_preview_text);
        }
    }
}

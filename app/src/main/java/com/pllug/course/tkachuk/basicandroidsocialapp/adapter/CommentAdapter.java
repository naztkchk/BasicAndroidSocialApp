package com.pllug.course.tkachuk.basicandroidsocialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> commentsList = new ArrayList<>();

    public CommentAdapter (Context context, List<Comment> commentsList){
        this.mContext = context;
        this.commentsList = commentsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_comments, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment comment = commentsList.get(position);
        holder.email_tv.setText(comment.getEmail());
        holder.body_tv.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView email_tv;
        public TextView body_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            email_tv = itemView.findViewById(R.id.row_view_comment_email_tv);
            body_tv = itemView.findViewById(R.id.row_view_comment_body_tv);
        }
    }
}

package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.post.PostWithCommentsFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.ItemClickListener;
import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> postList = new ArrayList<>();
    FragmentManager fragmentManager;

    PostWithCommentsFragment postWithCommentsFragment;

    public PostAdapter( Context context, ArrayList<Post> list){
        this.mContext = context;
        this.postList = list;
        fragmentManager= ((AppCompatActivity) mContext).getSupportFragmentManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Post post = postList.get(position);

        holder.author_tv.setText(String.valueOf(post.getId()));

        holder.id_tv.setText(String.valueOf(post.getId()));
        holder.title_tv.setText(post.getTitle());
        holder.body_tv.setText(post.getBody());

        holder.see_comments_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Button click - id"+post.getId(), Toast.LENGTH_SHORT).show();

                Log.i("PostAdapter", "Button click - id"+post.getId());

                postWithCommentsFragment = new PostWithCommentsFragment();

                FragmentManager fragmentManager =((AppCompatActivity)mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_container, postWithCommentsFragment)
                        .addToBackStack(null)
                        .commit();
                fragmentManager.executePendingTransactions();
                postWithCommentsFragment.setPost(post);
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void Onclick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(mContext, "Long click - id"+post.getId(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "click - id"+post.getId(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView author_tv;
        public TextView id_tv;
        public TextView title_tv;
        public TextView body_tv;

        Button see_comments_btn;

        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            author_tv = itemView.findViewById(R.id.row_post_author_tv);
            id_tv = itemView.findViewById(R.id.row_post_id_tv);
            title_tv = itemView.findViewById(R.id.row_post_title_tv);
            body_tv = itemView.findViewById(R.id.row_post_body_tv);

            see_comments_btn = itemView.findViewById(R.id.row_post_see_comments_btn);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.Onclick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.Onclick(view, getAdapterPosition(), true);
            return true;
        }
    }
}

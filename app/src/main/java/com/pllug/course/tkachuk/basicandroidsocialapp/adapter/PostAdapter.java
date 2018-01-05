package com.pllug.course.tkachuk.basicandroidsocialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.fragment.ProfilesFragment;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Post;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Profile;
import com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> postList = new ArrayList<>();
    ProfileRepository profileRepository;

    public PostAdapter(Context context, ArrayList<Post> list){
        this.mContext = context;
        this.postList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postList.get(position);

//        ProfileRepository profileRepository = new ProfileRepository();
//        new ProfilesFragment().loadData();
//        if(profileRepository.getById(post.getId())!= null){
//
//            Profile profile = profileRepository.getById(post.getId());
//            holder.author_tv.setText(profile.getName());
//        }
//        else
            holder.author_tv.setText(String.valueOf(post.getId()));

        holder.id_tv.setText(String.valueOf(post.getId()));
        holder.title_tv.setText(post.getTitle());
        holder.body_tv.setText(post.getBody());

        //TODO get count of comments and set button text
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView author_tv;
        public TextView id_tv;
        public TextView title_tv;
        public TextView body_tv;

        Button see_comments_btn;


        public ViewHolder(View itemView) {
            super(itemView);

            author_tv = itemView.findViewById(R.id.row_post_author_tv);
            id_tv = itemView.findViewById(R.id.row_post_id_tv);
            title_tv = itemView.findViewById(R.id.row_post_title_tv);
            body_tv = itemView.findViewById(R.id.row_post_body_tv);

            see_comments_btn = itemView.findViewById(R.id.row_post_see_comments_btn);
        }
    }
}

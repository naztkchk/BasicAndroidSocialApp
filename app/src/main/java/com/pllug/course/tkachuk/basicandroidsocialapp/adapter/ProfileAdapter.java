package com.pllug.course.tkachuk.basicandroidsocialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context mContext;
    private List<Profile> profileList = new ArrayList<>();

    public ProfileAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.profileList = list;
    }

    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Profile profile = profileList.get(position);
        holder.name_tv.setText(profile.getName());
        holder.id_tv.setText(String.valueOf(profile.getId()));
        holder.email_tv.setText(profile.getEmail());
    }


    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name_tv;
        public TextView id_tv;
        public TextView email_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.row_profile_name_tv);
            id_tv = itemView.findViewById(R.id.row_profile_id_tv);
            email_tv = itemView.findViewById(R.id.row_profile_email_tv);
        }
    }
}

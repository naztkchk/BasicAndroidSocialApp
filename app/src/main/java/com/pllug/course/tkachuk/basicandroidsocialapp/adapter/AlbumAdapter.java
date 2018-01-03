package com.pllug.course.tkachuk.basicandroidsocialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context mContext;
    private List<Album> albumList = new ArrayList<>();

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.mContext = context;
        this.albumList = albumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_album, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.textViewTitle.setText(String.valueOf(album.getTitle()));
        holder.textViewId.setText(String.valueOf(album.getId()));
        holder.textViewUserId.setText(String.valueOf(album.getUserId()));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //define the View objects
        public  TextView textViewTitle;
        public  TextView textViewId;
        public  TextView textViewUserId;

        public ViewHolder (View view){
            super(view);
            //initialize the View objects
            textViewTitle =  view.findViewById(R.id.album_title_tv);
            textViewId =  view.findViewById(R.id.album_id_tv);
            textViewUserId =  view.findViewById(R.id.album_userId_tv);
        }
    }

}

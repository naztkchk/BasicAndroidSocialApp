package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pllug.course.tkachuk.basicandroidsocialapp.R;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private Context mContext;
    private List<Todo> todoList = new ArrayList<>();

    public TodoAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.todoList = list;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Todo todo = todoList.get(position);
        holder.userId_tv.setText(String.valueOf(todo.getUserId()));
        holder.id_tv.setText(String.valueOf(todo.getId()));
        holder.completed_cb.setText(todo.getTitle());
        holder.completed_cb.setChecked(todo.isCompleted());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userId_tv;
        public TextView id_tv;
        public CheckBox completed_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            userId_tv = itemView.findViewById(R.id.row_todo_userId_tv);
            id_tv = itemView.findViewById(R.id.row_todo_id_tv);
            completed_cb = itemView.findViewById(R.id.row_todo_completed_cb);
        }
    }
}

package com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory;

import android.util.Log;

import com.pllug.course.tkachuk.basicandroidsocialapp.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRepository implements IRepository{

    private static ArrayList<Comment> commentsList;

    public CommentRepository (ArrayList<Comment> list){
        commentsList = list;
    }

    public ArrayList<Comment> getCommentsListToPost(int postId){

        ArrayList<Comment> tempList = null;

        for(Comment c : commentsList){
            if(c.getPostId() == postId){
                Log.i("getCommentsListToPost", "comment - "+c.getName());
                tempList.add(c);
            }
        }
        return tempList;
    }

    @Override
    public List getList() {
        return commentsList;
    }

    @Override
    public void create(Object item) {
        commentsList.add((Comment) item);
    }

    @Override
    public void update(Object item) {
    }

    @Override
    public void remove(Object item) {
        commentsList.remove(item);
    }
}

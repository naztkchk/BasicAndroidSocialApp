package com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory;

import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Post;

import java.util.ArrayList;

public class PostRepository implements IRepository {

    private static ArrayList<Post> postList;

    public PostRepository(ArrayList<Post> list){
        this.postList = list;
    }

    public ArrayList<Post> getById(final int id){
        for(Post a: postList){
            if(a.getId() == id)
            {
                ArrayList<Post> tempList = new ArrayList<>();
                tempList.add(a);
                return tempList;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Post> getList() {
        return postList;
    }

    @Override
    public void create(Object item) {
        postList.add((Post) item);
    }

    @Override
    public void update(Object item) {
        for(Post a: postList){
            if(a.equals(item))
                a = (Post) item;
        }
    }

    @Override
    public void remove(Object item) {
        postList.remove(item);
    }
}

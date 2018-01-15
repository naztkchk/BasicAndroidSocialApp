package com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory;

import com.pllug.course.tkachuk.basicandroidsocialapp.model.Todo;

import java.util.ArrayList;

public class TodoRepository implements IRepository {

    private static ArrayList<Todo> todoList;

    public TodoRepository(ArrayList<Todo> list){
        this.todoList = list;
    }

    public ArrayList<Todo> getById(final int id){
        for(Todo a: todoList){
            if(a.getId() == id)
            {
                ArrayList<Todo> tempList = new ArrayList<>();
                tempList.add(a);
                return tempList;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Todo> getList() {
        return todoList;
    }

    @Override
    public void create(Object item) {
        todoList.add((Todo) item);
    }

    @Override
    public void update(Object item) {
        for(Todo a: todoList){
            if(a.equals(item))
                a = (Todo) item;
        }
    }

    @Override
    public void remove(Object item) {
        todoList.remove(item);
    }
}

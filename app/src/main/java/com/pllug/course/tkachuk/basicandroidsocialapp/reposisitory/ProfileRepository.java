package com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory;

import com.pllug.course.tkachuk.basicandroidsocialapp.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepository implements IRepository{

    private static ArrayList<Profile> profileList;

    public ProfileRepository(ArrayList<Profile> list){
        this.profileList = list;
    }

    public Profile getById(final int id){
        for(Profile a: profileList){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    @Override
    public List getList() {
        return profileList;
    }

    @Override
    public void create(Object item) {
        profileList.add((Profile) item);
    }

    @Override
    public void update(Object item) {
        for(Profile a: profileList){
            if(a.equals(item))
                a = (Profile) item;
        }
    }

    @Override
    public void remove(Object item) {
        profileList.remove(item);
    }
}

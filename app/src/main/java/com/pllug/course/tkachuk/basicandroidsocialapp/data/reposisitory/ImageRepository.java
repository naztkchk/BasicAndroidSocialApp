package com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory;

import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository implements IRepository{

    private static ArrayList<Image> imageList;

    public ImageRepository(ArrayList<Image> list) {
        imageList = list;
    }

    public ArrayList<Image> getByName(final String name){
        for(Image a: imageList){
            if(a.getTitle().equals(name))
            {
                ArrayList<Image> tempList = new ArrayList<>();
                tempList.add(a);
                return tempList;
            }
        }
        return null;
    }

    @Override
    public List getList() {
        return imageList;
    }

    @Override
    public void create(Object item) {
        imageList.add((Image) item);
    }

    @Override
    public void update(Object item) {
        for(Image a: imageList){
            if(a.equals(item))
                a = (Image) item;
        }
    }

    @Override
    public void remove(Object item) {
        imageList.remove(item);
    }
}

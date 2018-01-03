package com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory;
import com.pllug.course.tkachuk.basicandroidsocialapp.model.Album;
import java.util.ArrayList;

public class AlbumRepository implements IRepository{

    private static ArrayList<Album> albumList;

    public AlbumRepository(ArrayList<Album> list) {
        albumList = list;
    }

    public ArrayList<Album> getByName(final String name){
        for(Album a: albumList){
            if(a.getTitle().equals(name))
            {
                ArrayList<Album> tempList = new ArrayList<>();
                tempList.add(a);
                return tempList;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Album> getList() {
        return albumList;
    }

    @Override
    public void create(Object item) {
        albumList.add((Album) item);
    }

    @Override
    public void update(Object item) {
        for(Album a: albumList){
            if(a.equals(item))
                a = (Album) item;
        }
    }

    @Override
    public void remove(Object item) {
        albumList.remove(item);
    }
}

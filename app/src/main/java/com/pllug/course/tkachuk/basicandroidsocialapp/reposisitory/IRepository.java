package com.pllug.course.tkachuk.basicandroidsocialapp.reposisitory;

import java.util.List;

public interface IRepository<T> {

    List <T> getList ();
    void create (T item);
    void update (T item);
    void remove (T item);
}

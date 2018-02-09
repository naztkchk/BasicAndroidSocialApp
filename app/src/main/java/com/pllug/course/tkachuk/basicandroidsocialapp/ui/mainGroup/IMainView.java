package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup;

import android.support.v7.widget.RecyclerView;

public interface IMainView {

    void showProgress();
    void hideProgress();

    void hideRefreshing();

    void showNotFound();
    void showNotInternetConnection();

    void setAdapter(RecyclerView.Adapter adapter);
    void setEnabledSearch(boolean b);
}

package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.profiles;

import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Profile;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.IMainView;

public interface IProfileView extends IMainView{
    void showProfile(Profile profile);
}

package com.pllug.course.tkachuk.basicandroidsocialapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class JSONParser {

    public static <T>ArrayList<T> getFromJSONtoArrayList (String jsonString, Type type) {
        if(!isValid(jsonString))
            return null;
        return new Gson().fromJson(jsonString, type);
    }

    private static boolean isValid(String jsonString) {
        try{
            new JsonParser().parse(jsonString);
            return true;
        }catch (JsonSyntaxException e){
            return false;
        }
    }
}

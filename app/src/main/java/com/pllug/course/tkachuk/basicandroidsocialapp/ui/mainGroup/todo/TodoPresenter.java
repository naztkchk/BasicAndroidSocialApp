package com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.ApiService;
import com.pllug.course.tkachuk.basicandroidsocialapp.api.RetroClient;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.model.Todo;
import com.pllug.course.tkachuk.basicandroidsocialapp.data.reposisitory.TodoRepository;
import com.pllug.course.tkachuk.basicandroidsocialapp.ui.mainGroup.adapter.TodoAdapter;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.InternetConnection;
import com.pllug.course.tkachuk.basicandroidsocialapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class TodoPresenter implements ITodoPresenter{

    private ITodoView todoView;
    private Context mContext;

    private String responseBody;
    private TodoRepository todoRepository;
    private RecyclerView.Adapter adapter;

    public TodoPresenter(ITodoView todoView, Context context) {
        this.todoView = todoView;
        this.mContext = context;
    }

    @Override
    public void loadData() {
        todoView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(mContext)) {
            todoView.showProgress();

            //Creating an object for our api interface
        ApiService api = RetroClient.getRetroClient();

        //Calling Json
        Call<JsonArray> jsonArrayCall = api.getTodos();

        //Enqueue Callback will be call when get response...

        jsonArrayCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try
                {
                    responseBody = response.body().toString();

                    Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
                    ArrayList<Todo> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                    todoRepository = new TodoRepository(arrayList);

                    setList();
                    todoView.setEnabledSearch(true);
                    todoView.hideProgress();
                    todoView.hideRefreshing();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
                todoView.hideProgress();
                todoView.hideProgress();
            }});
        }else{
            todoView.showNotInternetConnection();
            todoView.setEnabledSearch(false);
            todoView.hideProgress();
            todoView.hideRefreshing();
        }
    }

    @Override
    public void setList() {
        TodoAdapter todoAdapter = new TodoAdapter(mContext, todoRepository.getList());
        adapter = todoAdapter;
        todoView.setAdapter(adapter);
    }

    @Override
    public void searchTodoById(String id) {
        if (id.matches("") || todoRepository.getById(parseInt(id)) == null) {
            todoView.showNotFound();
        } else {
            adapter = new TodoAdapter(mContext,
                    todoRepository.getById(parseInt(id)));
            todoView.setAdapter(adapter);
        }
    }
}

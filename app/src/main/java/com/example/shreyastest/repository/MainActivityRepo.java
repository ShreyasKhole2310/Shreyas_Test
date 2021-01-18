package com.example.shreyastest.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shreyastest.beanclasses.ApiSearchResponse;
import com.example.shreyastest.beanclasses.Images;
import com.example.shreyastest.interfaces.GetImageResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityRepo {

    private static String BASE_URL = "https://api.imgur.com/3/";

    private Context context;
    private static volatile MainActivityRepo instance;
    public MutableLiveData<List<Images>> mutableLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static MainActivityRepo getInstance(Context context) {
        if (instance == null) {
            instance = new MainActivityRepo(context);
        }
        return instance;
    }

     private MainActivityRepo(Context context) {
        this.context = context;
    }

     public void getData(String str) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request originalrequest = chain.request();

            Request.Builder builder = originalrequest.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization",
                            "Client-ID 137cda6b5008a7c");

            Request newRequest = builder.build();

            return chain.proceed(newRequest);
        }).build();

        Log.d("SEarchString", str);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GetImageResponse responseQuery = retrofit.create(GetImageResponse.class);

        Single<ApiSearchResponse> responseSingle = responseQuery.getApiResponse(str);

      responseSingle.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(result -> result)
                .subscribe(new DisposableSingleObserver<ApiSearchResponse>() {

                    @Override
                    public void onSuccess(ApiSearchResponse apiSearchResponse) {
                        List<ApiSearchResponse> lstApiSearchResponse = new ArrayList<>();
                        List<Images> lstImages = new ArrayList<>();

                        for (ApiSearchResponse.Data lstData : apiSearchResponse.data) {
                            if(lstData.imagesArrayList != null ){
                               /* Log.e("Main Activity", "Response Size:"
                                        + lstData.imagesArrayList.size());*/
                                lstImages.addAll(lstData.imagesArrayList);
                            }
                        }

                        mutableLiveData.postValue(lstImages);
                       /* */
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RxError", e.toString());
                    }
                });
    }

    public void onDisponse(){

    }


}

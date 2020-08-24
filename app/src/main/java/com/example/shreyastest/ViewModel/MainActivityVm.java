package com.example.shreyastest.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shreyastest.BeanClasses.Images;
import com.example.shreyastest.Repository.MainActivityRepo;

import java.util.List;

public class MainActivityVm extends AndroidViewModel {

    private MainActivityRepo mainActivityRepo;

    public MainActivityVm(@NonNull Application application) {
        super(application);
        mainActivityRepo = MainActivityRepo.getInstance(application);
    }

    public MutableLiveData<List<Images>> getImagesMutableLiveData(){
        return mainActivityRepo.mutableLiveData;
    }

    public void setDispose(){
        mainActivityRepo.onDisponse();
    }

    public void searchWord(String str){
        mainActivityRepo.getData(str);
    }

}

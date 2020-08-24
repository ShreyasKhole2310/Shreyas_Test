package com.example.shreyastest.Activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shreyastest.ViewModel.MainActivityVm;
import com.example.shreyastest.R;
import com.example.shreyastest.Adapters.RecyclerGridAdapter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    RecyclerView gridView;

    ProgressBar progress_horizontal;

    private RecyclerGridAdapter gridAdapter;
    private MainActivityVm mainActivityVm;

    private CompositeDisposable disposable = new CompositeDisposable();

    SearchView etSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.img_Grid);
        etSearchText = findViewById(R.id.etSearchText);
        progress_horizontal = findViewById(R.id.progress_horizontal);

        gridAdapter = new RecyclerGridAdapter(this);
        gridView.setLayoutManager(new GridLayoutManager(this, 5));

        mainActivityVm = new ViewModelProvider(this).get(MainActivityVm.class);

        mainActivityVm.getImagesMutableLiveData().observe(this, lstImages -> {
            gridAdapter.setLstImages(lstImages);
            gridView.setAdapter(gridAdapter);
            progress_horizontal.setMax(gridAdapter.getLstImages().size() * 100);
        });

        gridAdapter.loadingProgress.observe(this, value -> {
//            progress_horizontal.setProgress(value);
            ObjectAnimator animation = ObjectAnimator.ofInt(progress_horizontal, "progress",
                    progress_horizontal.getProgress(), value * 100);
            animation.setDuration(500);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        });

        disposable.add(
                fromView(etSearchText)
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            mainActivityVm.searchWord(result);
                            progress_horizontal.setVisibility(View.VISIBLE);
                        })
        );

    }

    public static Observable<String> fromView(SearchView searchView) {

        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return true;
            }
        });

        return subject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityVm.setDispose();

        disposable.clear();
    }
}

package com.example.shreyastest.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shreyastest.adapters.RecyclerGridAdapter
import com.example.shreyastest.beanclasses.Images
import com.example.shreyastest.R
import com.example.shreyastest.viewmodel.MainActivityVm
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var progress_horizontal: ProgressBar
    private var gridAdapter: RecyclerGridAdapter? = null
    private var mainActivityVm: MainActivityVm? = null
    private val disposable = CompositeDisposable()
    var etSearchText: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etSearchText = findViewById(R.id.etSearchText)
        progress_horizontal = findViewById(R.id.progress_horizontal)
        gridAdapter = RecyclerGridAdapter(this)
        gridView.setLayoutManager(GridLayoutManager(this, 5))
        mainActivityVm = ViewModelProvider(this).get(MainActivityVm::class.java)
        mainActivityVm!!.imagesMutableLiveData.observe(this, Observer { lstImages: List<Images> ->
            gridAdapter!!.setLstImages(lstImages)
            gridView.setAdapter(gridAdapter)
            progress_horizontal.setMax(gridAdapter!!.getLstImages()!!.size * 100)
        })
        gridAdapter!!.loadingProgress.observe(this, Observer { value: Int ->
//            progress_horizontal.setProgress(value);
            val animation = ObjectAnimator.ofInt(progress_horizontal, "progress",
                    progress_horizontal.getProgress(), value * 100)
            animation.duration = 500
            animation.interpolator = DecelerateInterpolator()
            animation.start()
        })
        disposable.add(
                fromView(etSearchText)
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result: String? ->
                            mainActivityVm!!.searchWord(result)
                            progress_horizontal.setVisibility(View.VISIBLE)
                        }
        )

        navCoroutines.setOnClickListener {
            val intent = Intent(this, TestingCoroutines::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityVm!!.setDispose()
        disposable.clear()
    }

    companion object {
        fun fromView(searchView: SearchView?): Observable<String> {
            val subject = PublishSubject.create<String>()
            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
    }
}
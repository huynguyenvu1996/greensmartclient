package com.group07.greensmart.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.group07.greensmart.R;
import com.group07.greensmart.rest.ApiInterface;


import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by nguyenvuhuy on 4/3/18.
 */

public class BaseActivity extends AppCompatActivity {


//    private CompositeDisposable mCompositeDisposable;
//    protected CompositeSubscription compositeSubscription;
//    protected Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//
//    protected void onUnsubscribe() {
//        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
//            compositeSubscription.unsubscribe();
//            Log.e("TAG", "onUnsubscribe: ");
//        }
//    }
//
////    protected void addSubscribe(Observable observable, Subscriber subscriber) {
////        this.subscriber = subscriber;
////        if (compositeSubscription == null) {
////            compositeSubscription = new CompositeSubscription();
////        }
////        compositeSubscription.add(observable
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(subscriber));
////    }
//
//
//    protected void addSubscribe(Observable observable, Subscriber subscriber) {
//        mCompositeDisposable.add(observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber.onNext(),subscriber.onError(),subscriber.onComplete())
//    }
//
//    private void loadJSON() {
//
//        ApiInterface requestInterface = new Retrofit.Builder()
//                .baseUrl("")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(ApiInterface.class);
//
//        Disposable disposable = requestInterface.register()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, this::handleError, this::handleSuccess);
//
//        mCompositeDisposable.add(disposable);
//    }
}

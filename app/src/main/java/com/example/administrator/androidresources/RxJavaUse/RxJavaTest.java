package com.example.administrator.androidresources.RxJavaUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidresources.R;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

//RXJava学习中
public class RxJavaTest extends AppCompatActivity {

    public String TAG = "RxJavaTest";
    public TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        tv = findViewById(R.id.tx_rx);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trange();
                test1();
            }
        });
    }


    //转换类操作符
    public void trange(){
        //.map转换类
        Observable.fromArray(new Integer[]{1,2,3,4,5,6})
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                }).map(new Function<String, People>() {
            @Override
            public People apply(String s) throws Exception {
                return new People(s);
            }
        }).subscribe(new Observer<People>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(People people) {
                System.out.println(people);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });

    }

    //创建类操作符
    public void create(){
        //from
        Observable<Integer> ob1 = Observable.fromArray(new Integer[]{1,2,3,4});
        //just
        Observable<? extends Serializable> ob2 = Observable.just(1,"one",true,"two",2);
        //create
        Observable<Integer> ob3 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
            }
        });
        //rang范围内的数
        Observable ob4 = Observable.range(1,3);

        //Time每段时间内发送
        Observable ob5 = Observable.timer(1,TimeUnit.SECONDS);
        //interval也是每段时间内发送
        Observable ob6 = Observable.interval(1,TimeUnit.SECONDS);

        //repeat重复发送
        Observable ob7 = Observable.just(1,2,3).repeat();
    }





    //.combineLatest
    public void combindLast(){
        Observable<Integer> o1 = Observable.just(1,2,3,4,5,1,2);
        Observable<Integer> o2 = Observable.just(2,5,7,3);
        Observable<Integer> o3 = Observable.just(3,3,4,5,6,7);

        Observable.combineLatest(o1, o2, o3, new Function3<Integer, Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                /*System.out.println(integer+":"+integer2+":"+integer3);
                if(integer>=5 && integer2>=5 && integer3>=5){
                    System.out.println(integer+":"+integer2+":"+integer3);
                    return "ok";
                }*/
                return integer+":"+integer2+":"+integer3;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //开始的时候
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //.map变换
    public void test3(){
        final Integer[] myInteger = {1,3,1};
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(Integer val:myInteger){
                    emitter.onNext(val);
                }
                emitter.onComplete();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Function<Integer, String>() {
            //使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
            @Override
            public String apply(Integer integer) throws Exception {
                return "字符串"+integer;
            }
        })
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: 转型完毕"+s);
            }
        });
    }


    //.subscribeOn线程间切换，被观察者运行线程
    //.observeOn线程间切换，也就是观察者运行的线程
    public void test2(){
        final Integer[] myInteger = {1,3,1};
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(Integer val:myInteger){
                    emitter.onNext(val);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())//上面subscribe也就是被观察者运行所在线程
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });

    }


    //单纯的观察者模式
    public void test1(){
        final String[] names = {"1"};
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: 开始");
                //tv.setText("张三");

            }
            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: "+s);
                Log.e(TAG, "Thread: "+Thread.currentThread()+"3");
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: 失败");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: 完成");
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "Thread: "+Thread.currentThread()+"1");
                for(String name:names){
                    emitter.onNext(name);
                }
                emitter.onComplete();
            }
        });

        observable.subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Log.e(TAG, "Thread: "+Thread.currentThread()+"2");
                        return s;
                    }
                })
                .subscribe(observer);


    }



}

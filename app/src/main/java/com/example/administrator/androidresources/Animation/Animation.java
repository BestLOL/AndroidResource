package com.example.administrator.androidresources.Animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidresources.R;

public class Animation extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Button btView;
    /*ArrayAdapter<String> adapter;

    String[] str = {"21","231"};
    ArrayList<String> data = new ArrayList<>(Arrays.asList(str));
    ExecutorService executorService;*/

    private static final String TAG = "LoginActivity";
    private EditText edt_username, edt_password;
    private Button btn_login;
    private TextView tv_show;

    private MyThread myThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        /*textView = findViewById(R.id.tv_anim);
        //listView = findViewById(R.id.lv_list);
        btView = findViewById(R.id.bt_show);*/
        /*//获取运行最大线程数
        int count = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(count);
        adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,data);
        listView.setAdapter(adapter);*/


        //initView();
        /*edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);*/
        btn_login = findViewById(R.id.btn_login);
        tv_show = findViewById(R.id.tv_show);

        Log.d("主线程", "onCreate: "+Thread.currentThread());


    }

/*    @Override
    protected void onResume() {
        super.onResume();
        initEvent();
    }*/

    private void initEvent() {
       /* tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myThread = new MyThread(new lll() {
                    @Override
                    public void some() {
                        tv_show.setText("hahah");
                        Log.d("线程", "some: "+Thread.currentThread());
                    }
                });
                myThread.start();
            }
        });*/
/*        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myThread = new MyThread(new Show() {
                    @Override
                    public void showName() {
                        btn_login.setText("hahah");
                        Log.d("线程", "some: "+Thread.currentThread());
                    }
                });
                myThread.start();
            }
        });*/
    }

    private void initView() {
        /*edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);*/
        btn_login = findViewById(R.id.btn_login);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.view.animation.Animation animation = AnimationUtils.loadAnimation(Animation.this,R.anim.view_anim);
                tv_show.startAnimation(animation);
                //addItem();

                //ObjectAnimator.ofInt(textView,"width",500).setDuration(4000).start();

                /*myThread = new MyThread(new lll() {
                    @Override
                    public void some() {
                        btView.setText("hahah");
                        Log.d("线程", "some: "+Thread.currentThread());
                    }
                });
                myThread.start();*/
            }
        });

/*        ValueAnimator animator = ValueAnimator.ofInt(1,2,3,4);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取改变后的值
                int current = (int) animation.getAnimatedValue();
                Log.e("Animation", current+"" );
                textView.setTexttextView(current+"");
            }
        });
        animator.start();*/

    }

    /*public void addItem(){
        Random dom = new Random();
        adapter.add(dom.nextInt()+"当天条目");
    }*/

    class MyThread extends Thread{
        Show show;
        MyThread(Show show){
            this.show = show;
        }

        @Override
        public void run() {
            super.run();
            Log.e("线程", "run: "+Thread.currentThread());
            show.showName();
        }
    }

    interface Show{
        void showName();
    }
}

package com.example.administrator.androidresources.Animation;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.androidresources.R;

public class AsyncTaskNow extends Activity {

    // 线程变量
    MyAsyncTask mTask;

    // 主布局中的UI组件
    Button button,cancel; // 加载、取消按钮
    TextView text; // 更新的UI组件
    ProgressBar progressBar; // 进度条



    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        button = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTask = new MyAsyncTask();
                    }
                });
                t1.start();
                try {
                    t1.join();
                    mTask.execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTask.cancel(true);
                        mTask = new MyAsyncTask();
                    }
                }).start();

            }
        });

    }

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     *   a. 继承AsyncTask类
     *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *      此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     */
     private class MyAsyncTask extends AsyncTask<String,Integer,String>{

        //方法1 任务执行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("正在加载中...");
        }

        //方法2 正式执行任务
        @Override
        protected String doInBackground(String... strings) {
            int length = 1;
            while(count<99){
                count+=length;
                // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                publishProgress(count);

                if(isCancelled()){
                    return null;
                }

                try {
                    //表示执行耗时任务
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        //方法3 更新任务执行
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);
            text.setText("loading..."+values[0]+"%");
        }


        //方法4 任务执行完毕后
        //接收线程任务执行结果，将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            text.setText("加载完毕");
            progressBar.setProgress(0);
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
            text.setText("已取消");
            //progressBar.setProgress(0);
        }

    }
}

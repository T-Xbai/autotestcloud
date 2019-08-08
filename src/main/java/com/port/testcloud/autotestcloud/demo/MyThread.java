package com.port.testcloud.autotestcloud.demo;

import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @ClassName: MyThread
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-08 17:57
 * @Description:
 */
public class MyThread extends Thread {

    private int count = 5;



    @Override
    public synchronized void run() {
        super.run();
        while (count>0){
            Random random = new Random();

            try {
                Thread.sleep( random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
            System.out.println("由"
                    .concat(MyThread.currentThread().getName())
                    .concat(" 计算，count：")
                    .concat(String.valueOf(count)));
        }
    }



    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread a = new Thread(myThread,"a");
        Thread b = new Thread(myThread,"b");
        Thread c = new Thread(myThread,"c");
        a.start();
        b.start();
        c.start();
        System.out.println("end-----------------");


    }

}

class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("MyRunnable");
    }


    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        System.out.println("run end -----");
        System.out.println(thread.getId());
        System.out.println(thread.getName());

    }
}



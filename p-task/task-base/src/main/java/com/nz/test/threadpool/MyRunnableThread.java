package com.nz.test.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyRunnableThread implements Runnable {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private String type;

    private int i;

    private boolean flag;

    public MyRunnableThread(String type, int i, boolean flag) {
        this.type = type;
        this.i = i;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Thread.sleep(1000 * 2);
                flag = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println((i >= 10 ? i : "0" + i) + ". " + Thread.currentThread().getName() + " - " + type);
            }
        }
    }
}

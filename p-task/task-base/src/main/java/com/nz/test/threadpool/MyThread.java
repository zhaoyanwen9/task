package com.nz.test.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread implements Runnable {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int i;

    private boolean flag;

    public MyThread(int i, boolean flag) {
        this.i = i;
        this.flag = flag;
    }

    @Override
    public void run() {
        String sTime = simpleDateFormat.format(new Date());
        String eTime = "-/-";
        while (flag) {
            try {
                Thread.sleep(1000 * 5);
                flag = false;
                eTime = simpleDateFormat.format(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("\t" + sTime + " " + eTime + " " + Thread.currentThread().getName());
            }
        }
    }
}

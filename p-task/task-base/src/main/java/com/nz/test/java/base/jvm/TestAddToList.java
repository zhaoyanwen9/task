package com.nz.test.java.base.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * ----使用锁，维护计数器的串行访问与安全性
 */
public class TestAddToList implements Runnable {

    /**
     * 非线程同步,越界
     */
    public static List<Integer> numberList = new ArrayList<Integer>();

    int startNum = 0;

    public TestAddToList(int startNum) {
        this.startNum = startNum;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < 1000000) {
            numberList.add(startNum);
            startNum += 2;
            count++;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new TestAddToList(0));
        Thread t2 = new Thread(new TestAddToList(1));
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {
            Thread.sleep(1);
        }
        System.out.println(numberList.size());
    }
}

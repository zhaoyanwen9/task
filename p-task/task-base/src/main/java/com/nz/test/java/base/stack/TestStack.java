package com.nz.test.java.base.stack;

import java.util.EmptyStackException;
import java.util.Stack;

public class TestStack {
    static void showpush(Stack<Integer> st, int a) {
        st.push(new Integer(a));
        System.out.println("push(" + a + ")" + "\t" + "stack: " + st);
    }

    static void showpop(Stack<Integer> st) {
        Integer a = (Integer) st.pop();
        System.out.println("pop -> " + a + "\t" + "stack: " + st);
    }

    public static void main(String args[]) {
        Stack<Integer> st = new Stack<Integer>();
        System.out.println("stack: " + st);
        showpush(st, 42);
        showpush(st, 66);
        showpush(st, 99);
        showpop(st);
        showpop(st);
        showpop(st);
        try {
            showpop(st);
        } catch (EmptyStackException e) {
            System.out.println("empty stack");
        }
    }
}

package com.nz.test.java.base.collection;

public class Student implements Comparable<Student>{
    private String name;
    private int age;

    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Student(int age) {
        this.age = age;
    }

    public Student(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public int compareTo(Student s) {
        //return -1; //-1表示放在红黑树的左边,即逆序输出
        //return 1;  //1表示放在红黑树的右边，即顺序输出
        //return o;  //表示元素相同，仅存放第一个元素
        return this.age - s.age;
    }
}

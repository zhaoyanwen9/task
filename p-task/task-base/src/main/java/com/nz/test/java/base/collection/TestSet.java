package com.nz.test.java.base.collection;

import java.util.*;

public class TestSet {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        TreeSet<String> treeSet = new TreeSet<>();
        for (String data : Arrays.asList("B", "E", "D", "C", "A", "W","M","M")) {
            hashSet.add(data);
            linkedHashSet.add(data);
            treeSet.add(data);
        }

        List<String> stringList = Arrays.asList("B", "E", "D", "C", "A", "W");
        for (int i = 0; i < stringList.size(); i++) {
            hashSet.add(stringList.get(i));
            linkedHashSet.add(stringList.get(i));
            treeSet.add(stringList.get(i));
        }

        //不保证有序
        System.out.println("HashSet :" + hashSet + "(无序)");
        //FIFO保证安装插入顺序排序
        System.out.println("LinkedHashSet :" + linkedHashSet + "(FIFO)");
        //内部实现排序
        System.out.println("TreeSet :" + treeSet + "(有序:按照某种规则进行排序 由于不知道该安照那一中排序方式排序，所以会报错。解决方法： 1.自然排序 2.比较器排序)");

        TreeSet<Integer> ts = new TreeSet<Integer>();
        ts.add(20);
        ts.add(18);
        ts.add(23);
        ts.add(22);
        ts.add(17);
        ts.add(24);
        ts.add(19);
        ts.add(18);
        ts.add(24);
        System.out.println("TreeSet :" + treeSet + "(自然排序:基本数据类型)" + ts);
        for (Integer i : ts) {
            System.out.println("for each: "+i);
        }

        Iterator<Integer> ite = ts.iterator();
        while (ite.hasNext()){
            System.out.println("迭代器: "+ite.next());
        }

        for (; ts.size() > 0; ) {
            System.out.println("for循环: "+ts.size() + "\t" + ts.pollFirst());
        }

        System.out.println("TreeSet :" + treeSet + "(有序:引用数据类型) 自然排序 1.实现 Comparable接口 2.重写Comparable接口中的Compareto方法");
        TreeSet<Student> ts1 = new TreeSet<Student>();
        //创建元素对象
        Student s1 = new Student("zhangsan", 20);
        Student s2 = new Student("lis", 22);
        Student s3 = new Student("wangwu", 24);

        //将元素对象添加到集合对象中
        ts1.add(s1);
        ts1.add(s2);
        ts1.add(s3);

        //增强for遍历集合：增强for循环（也称for each循环）是JDK1.5以后出来的一个高级for循环，专门用来遍历数组和集合。其内部原理是一个Iteration迭代器，在遍历的过程中，不能对集合中的元素进行增删操作。
        for (Student s : ts1) {
            System.out.println(s.getName() + "-----------" + s.getAge());
        }


        System.out.println("TreeSet :" + treeSet + "(有序:引用数据类型) 比较器排序 1.继承Comparator接口 2.重写Comparator接口中的Compare方法");
        TreeSet<Student> ts2 = new TreeSet<Student>(new MyComparator());

        //创建元素对象
        Student s4 = new Student("zhangsan", 20);
        Student s5 = new Student("lis", 22);
        Student s6 = new Student("wangwu", 24);
        //将元素对象添加到集合对象中
        ts2.add(s4);
        ts2.add(s5);
        ts2.add(s6);
        //增强for遍历集合
        for (Student s : ts2) {
            System.out.println(s.getName() + "-----------" + s.getAge());
        }


        // 性能比较  TreeSet要慢得多,因为它是有序的。
        Random r = new Random();
        HashSet<Student> hashSet1 = new HashSet<Student>();
        TreeSet<Student> treeSet1 = new TreeSet<Student>();
        LinkedHashSet<Student> linkedSet1 = new LinkedHashSet<Student>();

        // start time
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int x = r.nextInt(1000 - 10) + 10;
            hashSet1.add(new Student(x));
        }

        // end time
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("HashSet: " + duration);

        // start time
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int x = r.nextInt(1000 - 10) + 10;
            treeSet1.add(new Student(x));
        }
        // end time
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("TreeSet: " + duration);

        // start time
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int x = r.nextInt(1000 - 10) + 10;
            linkedSet1.add(new Student(x));
        }

        // end time
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedHashSet: " + duration);
    }
}

package com.nz.test.java.base;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JavaBase {

    private static final Logger logger = LoggerFactory.getLogger(JavaBase.class);

    /**
     * psvm sout
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * String、StringBuilder、StringBuffer
         * 1.三者的速度快慢大概为：StringBuild>StringBuffer> String
         *     String是字符串的常量，String一旦创建就不可更改，新的String其实是新的常量，String只能被创建不能被修改，
         *     StringBuild和StringBuffer是字符串变量，StringBuild和StringBuffer是可更改的，下面以代为为例
         * 2.在线程上，StringBuilder是线程不安全的，StringBuffer是线程安全的
         * 3.总结
         *   3.1 String ：不可变类，任何对String的改变都会引发新的String对象的生成，适用于少量的字符串操作的情况
         *   3.2 StringBuffer ：线程安全，任何对它所指代的字符串的改变都不会产生新的对象，适用多线程下在字符缓冲区进行大量操作的情况
         *   3.3 StringBuilder ：线程不安全，因此不适合多线程中使用，适用于单线程下在字符缓冲区进行大量操作的情况
         *
         */

        /**
         * 在jvm中代码是这样处理的,首先创建一个String对象str并把abc赋值给str(可能创建一个或者不创建对象,当xxx这个字符常量在String常量池不存在，会在String常量池创建一个String对象，然后a会指向这个内存地址，后面继续用这种方式继续创建xxx字符串常量，始终只有一个内存地址被分配。)
         *
         */
        String str = "abc";
        logger.info("{}", str);

        /**
         * 字符串缓冲区
         * 通过字符数组来构建一个字符串常量
         */
        char data[] = {'a', 'b', 'c'};
        String _str = new String(data);

        /**
         * 直接通过new关键字来创建对象(至少创建一个对象，也有可能两个。只要用到new就肯定在堆上创建一个String对象，并且检查String常量池是否存在，如果不存在就会在Stirng常量池创建这个String对象，存在就不创建。)
         */
        String str_ = new String("abc");

        /**
         * equasl方法的作用
         *   判断两个变量是否对同一个对象的引用，也就是内存堆中的内容是否相同,其实就是对String对象所封装的字符串内容作比较，具体的值是否相等，如果两个String对象所封装的字符型内容相同，则equals()方法将返回true。
         *
         * ==操作符的作用
         *  1.比较基本数据类型是否相同
         *  2.判断引用是否指向堆内存的同一块地址，也就是是否指向同一个对象
         */
        logger.info("{}", str.equals(_str));
        logger.info("{}", str.equals(str_));
        logger.info("{}", str == _str);
        logger.info("{}", str == str_);

        /**
         * jvm其实又创建了一个新的对象也叫str，并把原来的str的值赋值和“de”赋值给新的str,而原来的str会被垃圾回收机制（GC）给回收掉了，所以str并没有被修改，也就是String一旦被创建后就不可更改，所以，Java中对String对象进行的操作实际上是一个不断地创建新的对象并且将旧的对象回收的过程，所以实行速度很慢。
         */
        str = str + "de";
        logger.info(str);

        /**
         * str="abcde"；
         * string builder: append方法始终将这些字符添加到生成器的末端；而 insert方法则在指定的点添加字符。
         * string buffer: append方法始终将这些字符添加到缓冲区的末端，而insert方法则在指定的点添加字符
         */
        String str1 = "abc" + "de";
        StringBuilder stringBuilder = new StringBuilder().append("abc").append("de");
        logger.info("{}", str);
        logger.info("{}", stringBuilder);

        String str2 = "abc";
        String str3 = "de";
        String str4 = str2 + str3;
        logger.info(str4);

        /**
         * 1.常见的集合:
         *   Map接口和Collection接口是所有集合框架的父接口：
         *     Collection接口的子接口包括：Set接口和List接口
         *     Map接口的实现类主要有：HashMap、TreeMap、Hashtable、ConcurrentHashMap以及Properties等
         *     Set接口的实现类主要有：HashSet、TreeSet、LinkedHashSet等
         *     List接口的实现类主要有：ArrayList、LinkedList、Stack以及Vector等
         * 2.HashMap与HashTable的区别？
         *   HashMap没有考虑同步，是线程不安全的；Hashtable使用了synchronized关键字，是线程安全的；
         *   HashMap允许K/V都为null；后者K/V都不允许为null；
         *   HashMap继承自AbstractMap类；而Hashtable继承自Dictionary类；
         *
         * 3.HashMap的put方法的具体流程？ 计算索引、逻辑判断 扩容 链表 红黑树
         * 4.HashMap的扩容操作是怎么实现的？HashMap通过resize()方法进行扩容或者初始化的操作
         * 5.HashMap是怎么解决哈希冲突的？
         * 6.HashMap的数据结构: 在Java中，保存数据有两种比较简单的数据结构：数组和链表。数组的特点是：寻址容易，插入和删除困难；链表的特点是：寻址困难，但插入和删除容易；
         * 7.为什么HashMap中String、Integer这样的包装类适合作为K？String、Integer等包装类的特性能够保证Hash值的不可更改性和计算准确性，能够有效的减少Hash碰撞的几率
         * 8.ArrayList 和 Vector 的区别？这两个类都实现了 List 接口（List 接口继承了 Collection 接口），他们都是有序集合，即存储在这两个集合中的元素位置都是有顺序的，相当于一种动态的数组，我们以后可以按位置索引来取出某个元素，并且其中的数据是允许重复的，这是与 HashSet 之类的集合的最大不同处，HashSet 之类的集合不可以按索引号去检索其中的元素，也不允许有重复的元素。
         *   同步性：Vector 是线程安全的，ArrayList 是线程不安全的
         * 9.ArrayList和LinkedList的区别？
         *   LinkedList 实现了 List 和 Deque 接口，一般称为双向链表；ArrayList 实现了 List 接口，动态数组；
         *   LinkedList 在插入和删除数据时效率更高，ArrayList 在查找某个 index 的数据时效率更高；
         *   LinkedList 比 ArrayList 需要更多的内存；
         */

        Map<String,Object> map = new HashMap<>();
        map.put(null,null);
        logger.info(new Gson().toJson(map));
    }
}

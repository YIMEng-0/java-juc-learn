package com.luobin.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Doraemon
 * @date 2022/3/30 3:34 下午
 * @version 1.0
 */
@Slf4j(topic = "c.TestBiased")
public class TestBiased {
    interface HelloWorld {

        void hello();
    }

    public static void main(String[] args) {

        // 1、继承 Thread 类 然后创建继承了 Thread 的类创建对象，调用 start 方法；比较麻烦
        // 直接使用匿名内部类进行方法的实现，进而创建线程，减少了继承的麻烦；直接匿名内部直接实现

        // 2、实现 Runnable 传入Thread // 可以使用 lambda 进行方法 3 的优化

        // 3、使用 Callable 创建 FutureTask 将 Callable 传递进去即可；


        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };

        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };

        Dog d = new Dog();
    }
}

class Dog {

}

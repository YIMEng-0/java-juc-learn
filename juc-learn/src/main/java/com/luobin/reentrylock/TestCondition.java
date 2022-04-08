package com.luobin.reentrylock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Doraemon
 * @date 2022/4/3 6:20 下午
 * @version 1.0
 */
public class TestCondition {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();



        condition1.signal();
        condition2.signalAll();

    }
}

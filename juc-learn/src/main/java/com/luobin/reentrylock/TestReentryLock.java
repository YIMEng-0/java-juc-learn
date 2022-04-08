package com.luobin.reentrylock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Doraemon
 * @date 2022/4/3 5:49 下午
 * @version 1.0
 */

@Slf4j(topic = "c.ReentryLock")
public class TestReentryLock {
    private static ReentrantLock lock = new ReentrantLock();
    // lock = 普通对象 + Monitor

    public static void main(String[] args) {
        lock.lock();
        try {
            log.debug("enter main");
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1() {
        lock.lock();
        try {
            log.debug("enter m1");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        lock.lock();
        try {
            log.debug("enter m2");
        } finally {
            lock.unlock();
        }
    }
}

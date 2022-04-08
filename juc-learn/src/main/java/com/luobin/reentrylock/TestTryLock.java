package com.luobin.reentrylock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Doraemon
 * @date 2022/4/3 6:06 下午
 * @version 1.0
 */
@Slf4j(topic = "c.TestTryLock")
public class TestTryLock {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 =  new Thread(()->{
            log.debug("尝试获取锁");

            if (!lock.tryLock()) {
                log.debug("超过了等待时间，没有获取到锁");
                return;
            }

            try {
                log.debug("获取到了锁，可以继续执行");
            }finally {
                lock.unlock();
            }
        },"t1");

        t1.start();

    }
}

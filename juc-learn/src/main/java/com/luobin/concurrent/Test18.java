package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/31 8:26 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test18")
public class Test18 {
    // 希望锁住的对象一直是同一个对象，所以加上去了 final 不可以发生变化
    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                log.debug("新建的线程获取到了锁");
                synchronized (lock) {

                    // sleep() 方式是不会释放锁的，其他的线程来了需要去 BLOCKED 等待
                    Thread.sleep(20000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        synchronized (lock) {
            log.debug("主线程获取到了锁");
        }
    }
}

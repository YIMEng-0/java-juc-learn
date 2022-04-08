package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 3:29 下午
 * @version 1.0
 */

/**
 * 线程与任务分离开
 *
 * Runnable 里面定义了任务
 *
 * 然后传递到 Thread 进行线程
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        /**
         *         Runnable r = new Runnable() {
         *             @Override
         *             public void run() {
         *                 log.debug("running");
         *             }
         *         };
         */
        Runnable r = () -> {log.debug("running");};

        Thread t = new Thread(r);
        t.setName("t2");
        t.start();

        log.debug("main is running");
    }
}

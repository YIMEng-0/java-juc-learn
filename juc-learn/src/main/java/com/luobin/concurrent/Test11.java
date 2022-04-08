package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 7:11 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test11")
public class Test11 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
           log.debug("sleeping...");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        t1.start();
        // 目的是为了先让 t1 线程进入睡眠，然后再打断，否则还没有睡眠直接就打断了，不太正确

        Thread.sleep(5000);
        Thread.sleep(1000);

        log.debug("interrupt");

        t1.interrupt();

        log.debug("打断标记{}: ",t1.isInterrupted());
    }
}

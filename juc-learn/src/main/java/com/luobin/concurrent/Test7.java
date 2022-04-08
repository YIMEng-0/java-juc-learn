package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 6:23 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {

                try {
                    log.debug("enter sleeping");
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };

        t1.start();

        Thread.sleep(1000);
        log.debug("t1 will interrupt...");
        t1.interrupt();
    }
}

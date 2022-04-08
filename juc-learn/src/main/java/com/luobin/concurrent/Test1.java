package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 3:19 下午
 * @version 1.0
 */

@Slf4j(topic = "c.test")
public class Test1 {
    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        t.setName("t1");
        t.start();

        log.debug("main is running");
    }
}
package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 3:56 下午
 * @version 1.0
 */

@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) {
        new Thread(()->{
            while (true) {
                log.debug("running");
            }
        },"t1").start();

        new Thread(()->{
            while (true) {
                log.debug("running");
            }
        },"t2").start();
    }
}

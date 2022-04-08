package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Target;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Doraemon
 * @date 2022/3/29 11:16 上午
 * @version 1.0
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
           log.debug("parking");
            LockSupport.park();

            log.debug("unpark");
            log.debug("打断状态：{}",Thread.currentThread().isInterrupted());


        },"t1");
        t1.start();

        // 放置还没有进入 park 直接打断
        Thread.sleep(1000);

        // 打断 park 的状态
        t1.interrupt();
    }
}

package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 7:24 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test12")

public class Test12 {
    public static void main(String[] args) throws InterruptedException {

        // 实际上使用的是创建的 Thread 线程的构造器 (Runnable,"name")
        // 由于里面没有输入的参数，直接使用 lambda 表达式即可
        // 任何对象在创建的时候，都是会使用构造函数的，构造函数使用什么的形式，将相关的参数进行传递即可
        Thread t1 = new Thread(() -> {
            while (true) {
                // 主线程中虽然让 t1 线程进行打断，但是具体是不是打断需要 t1 线程自己决定，主线程打断之后
                // 打断标识在这里会变成真的，所以可以使用打断标识进行正确的打断即可
                boolean interrupt = Thread.currentThread().isInterrupted();
                if (interrupt) {
                    log.debug("被打断了，退出去循环");
                    break;
                }
            }
        }, "t1");

        t1.start();

        // 为什么需要主线程先 sleep() 一秒钟呢？为了让 t1 线程先运行一下，不然 t1 还没有sleep 就打断了 程序没有达到自己的期望输出
        Thread.sleep(1);

        log.debug("interrupt");

        // 这个时候使用了打断标识，可以合理的将线程进行打断处理
        t1.interrupt();
    }
}

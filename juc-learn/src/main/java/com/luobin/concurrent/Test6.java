package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 6:18 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test6")
public class Test6 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程启动之后的状态是： " + t1.getState());
    }
}

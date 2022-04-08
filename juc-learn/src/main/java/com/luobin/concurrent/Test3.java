package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Doraemon
 * @date 2022/3/28 3:46 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running...");
                return 100;
            }
        });

        Thread t = new Thread(task, "t1");

        t.start();

        // 获取返回结果 主线程得到的返回结果
        log.debug("{}", task.get());

        log.debug("main is running...");
    }
}

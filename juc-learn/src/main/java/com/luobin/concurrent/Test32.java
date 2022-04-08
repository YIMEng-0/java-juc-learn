package com.luobin.concurrent;

import com.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/4/3 7:30 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test32")
public class Test32 {
    static volatile boolean run = true;
    public static void main(String[] args) {
        Thread t = new Thread(()->{
           while (run) {
                log.debug("t1 线程在运行了");
           }
           log.debug("t1 线程停止运行了");
        });

        t.start();
        Sleeper.sleep(1);
        log.debug("main 里面停止 t 线程");
        run = false;
    }
}

package com.luobin.c1;

import com.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Doraemon
 * @date 2022/4/1 4:25 下午
 * @version 1.0
 */
@Slf4j(topic = "c.TestPark")
public class TestPark {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
           log.debug("start");
            Sleeper.sleep(2);

            log.debug("park...");
            LockSupport.park();

            log.debug("resume");
        },"t1");
        t1.start();



        Sleeper.sleep(1);
        log.debug("unPark...");
        LockSupport.unpark(t1);
    }
}

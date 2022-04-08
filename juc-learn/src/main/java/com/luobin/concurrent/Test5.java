package com.luobin.concurrent;

import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/28 6:13 下午
 * @version 1.0
 */

@Slf4j(topic = "c.Test5")
public class Test5 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {

                log.debug("running");
            }
        };

        // 获取到线程的相关信息
        System.out.println(t1.getState());

        t1.start();

        System.out.println(t1.getState());
    }
}

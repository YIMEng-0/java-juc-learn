package com.luobin.concurrent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Doraemon
 * @date 2022/3/29 3:34 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test17")
public class Test17 {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();

            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrease();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("count 现在的数值是：{}", room.getCounter());
    }
}

class Room {
    private int count = 0;

    public synchronized void increment() {
        // 锁住当前的对象 this 指代的就是当前的 Room 对象
        count++;
    }

    public synchronized void decrease() {
        count--;
    }

    public synchronized int getCounter() {
        return count;
    }
}

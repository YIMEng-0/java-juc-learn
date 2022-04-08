package com.luobin.concurrent;

import lombok.CustomLog;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.FutureTask;

/**
 * @author Doraemon
 * @date 2022/3/28 7:44 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test13")
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        Thread.sleep(3500);

        twoPhaseTermination.stop();
    }
}

@Slf4j(topic = "c.Two")
class TwoPhaseTermination {
    private Thread monitor;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("终止当前线程的执行");
                    break;
                }

                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    // 重新设置打断标志，因为是在异常中执行的，打断标志为 false,重新设置之后，为了打断标志为 真
                    // 打断标志会变为 true 线程才会被真正的终止
                    current.interrupt(); // 两次打断 终止线程的执行
                }
            }
        });

        monitor.start();
    }

    // 关闭监控线程
    public void stop() {
        // monitor 就是监测线程，这个是正常调用线程的 interrupt 方法，其他的方法也是可以调用的；
        monitor.interrupt();
    }
}

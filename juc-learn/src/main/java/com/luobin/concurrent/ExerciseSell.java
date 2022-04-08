package com.luobin.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @author Doraemon
 * @date 2022/3/29 8:40 下午
 * @version 1.0
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        // 模拟多个人同时进行买票
        TicketWindow1 ticketWindow = new TicketWindow1(1000);

        // 将所有的线程放到集合中
        // 因为 这个 ArrayList 不会被多个线程进行访问，所以这里可以使用这个；
        // 多线程下面就不能使用这个了
        List<Thread> threadList = new ArrayList<>();

        // 将卖出去的票放入到 List 里面，使用线程安全的 Vector
        List<Integer> sellAmount = new Vector<>();

        for (int i = 0; i < 200; i++) {
            Thread thread = new Thread(()->{
                // ticketWindow ticketWindow 是两个共享变量，所以不存在方法的组合之后就变得线程不安全
                // 如果这两个变量是同一个变量的两个方法那么就会产生错误
                int count  = ticketWindow.sell(randomAmount(5));
                sellAmount.add(count);
            });

            Thread.sleep(randomAmount(5));

            threadList.add(thread);
            thread.start();
        }

        // 将所有的线程添加到主线程中
        // 将线程全部加入到主线程是为了得到所有线程的运行结果，使得线程安全，否则全部线程的运行结果可能出错
        for (Thread thread : threadList) {
            log.debug("当前线程是：" + thread.getName());
            thread.join();
        }

        // 统计卖出去的票以及剩下来的票加起来等于 100000 说明是安全的；
        log.debug("剩余的票是： " + ticketWindow.getCount());

        log.debug("卖出去的票是 ：" + sellAmount.stream().mapToInt(i -> i).sum());
    }

    static Random random = new Random();

    // Random 返回伪随机数
    public static int randomAmount(int amount) {
        return random.nextInt(amount) + 1;
    }
}

class TicketWindow{
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    // 关于卖票 这个一定是需要是线程安全的；
    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= count;
            return amount;
        } else {
            return 0;
        }
    }
}

package com.luobin.c1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Doraemon
 * @date 2022/4/8 5:18 下午
 * @version 1.0
 */
public class BlockingQueue<T> {
    // 1、任务队列
    // 使用双向链表的数据结构
    private Deque<T> queue = new ArrayDeque<>();

    // 2、锁保护队列里面的元素
    private ReentrantLock lock = new ReentrantLock();

    // 3、生产者条件变量
    // 创建出来的任务不能太多，也是需要等待的；
    private Condition fullWaitSet = lock.newCondition();// 创建等待条件

    // 4、消费者的条件变量
    // 消费者没有锁的时候，需要等待
    private Condition emptyWaitSet = lock.newCondition();

    // 5、阻塞队列的容量上限
    private int capcity;

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            // 消费任务，空的话，需要进入等待状态
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 退出来循环，说明存在任务可以消费，直接从双向链表中取出来元素即可
            T t = queue.removeFirst();

            // 消费掉了一个任务，唤醒那边可以继续产生
            fullWaitSet.signal();
            return t;
        } finally {
            // 获取结束元素之后，释放锁
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T element) {
        lock.lock();
        try {
            // 存放任务的双向链表满了，线程进入等待状态
            while (queue.size() == capcity) {
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            queue.addLast(element);
            // 添加了一个元素之后，唤醒生产者线程
            // 生产者线程知道了队列不是空的，可以继续执行下去
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    // 获取大小
    public int size() {
        lock.lock();
        try {
            // 直接返回 queue 的大小即可
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    // 携带超时功能的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            // 将 timeout 统一转换为 纳秒
            long nanos = unit.toNanos(timeout);


            // 消费任务，空的话，需要进入等待状态
            while (queue.isEmpty()) {
                try {
                    // 等待时间 = 总的等待时间 - 经过的时间
                    // 自动的进行了一个处理
                    emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 退出来循环，说明存在任务可以消费，直接从双向链表中取出来元素即可
            T t = queue.removeFirst();

            // 消费掉了一个任务，唤醒那边可以继续产生
            fullWaitSet.signal();
            return t;
        } finally {
            // 获取结束元素之后，释放锁
            lock.unlock();
        }
    }
}

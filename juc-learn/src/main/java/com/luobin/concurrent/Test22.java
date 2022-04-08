package com.luobin.concurrent;

import com.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import com.util.Sleeper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;


/**
 * @author Doraemon
 * @date 2022/4/1 3:08 下午
 * @version 1.0
 */
@Slf4j(topic = "c.Test22")
public class Test22 {
    public static void main(String[] args) {
        // 对于消息队列的类进行测试使用；

        // 创建消息队列，进行放入消息以及取出来消息；
        MessageQueue queue = new MessageQueue(2);// 创建容量的大小直接使用构造方法指定即可


        // 下面床架三个生产者线程以及一个消费者线程进行测试

        for (int i = 0; i < 3; i++) {
            int id = i;

            new Thread(() -> {// lambda 引用外面的变量要求是 final 的，i 是可以变化的，不能使用
                queue.put(new Message(id, "value" + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
            // 进行消息的消费操作，每相隔一秒，进行消息的消费
            Sleeper.sleep(1);
            queue.take();
        }, "消费者").start();
    }
}


// 消息队列中保存消息，这李保存消息使用 LinkedList 双向链表即可
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    // 创建消息队列，使用双向链表
    private LinkedList<Message> list = new LinkedList<Message>();

    // 设置队列的容量
    private int capacity;

    // 使用构造方法，设置消息传递的容量，是动态变化的
    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    // 获取消息
    public Message take() {
        // 检查队列是否为空，空的话，进入等待状态

        // 线程进入等待的状态，拿到一个锁对象的 Monitor 的 WaitList 里面进行等待

        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空 消费者线程进入等待状态");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 取元素的时候从队列中的头部取出来；
        Message message = list.removeFirst();
        log.debug("消费者线程已消费数据： " + message);

        // 如果队列满的话，它进入等待了，消费了一个，有空位置了，唤醒生产的线程，开始工作
        list.notifyAll();

        // 返回双向链表的中头部的元素；当前这个第一个元素也是会被删除的；
        return message;
    }

    // 存入消息
    public void put(Message message) {
        synchronized (list) {
            // 检查队列是不是满的；
            while (list.size() == capacity) {
                // 满了之后，双向链表就可以进入到等待状态
                try {
                    log.debug("队列已满 生产者线程进入等待状态");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 没有满的话，将消息存放进去
            // 将新的消息放入到队列尾部中
            list.addLast(message);

            log.debug("已生产消息,放到了消息队列中： " + message);

            // 将正在等待 list 队列中放进去元素的线程唤醒，有消息可以消费了，可以醒来了；
            list.notifyAll();
        }
    }
}

// 存取消息的数据类型
// 存取的数据类型里面应该存在一个 id 用来区分消息
// id 作为线程之间的联系以及桥梁
final class Message {
    // 线程之间的通信是需要使用 id
    private int id;

    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    // 在一个类的内部中只有 get 方法说明类的内部是没有办法进行修改的；
    // 线程安全
    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
